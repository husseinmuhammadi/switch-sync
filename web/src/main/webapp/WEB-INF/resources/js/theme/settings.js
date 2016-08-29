jQuery(document).ready(function(){

	var pageUrl = window.location.href,
		hostName = window.location.hostname,
		isPreviewHost = hostName.match(/pi-themes\.com/),
		isBuild = pageUrl.match(/\/build/),
		currentColor = (isPreviewHost || isBuild) ? pageUrl.match(/\/(blue|green|lime|brown|orange|red|purple)\//) ? pageUrl.match(/\/(blue|green|lime|brown|orange|red|purple)\//)[1] : 'base' : 'base',
		currentBackground,
		cls_active = 'pi-active',
		cls_fixed = 'pi-fixed',
		$w = $(window),
		$b = $('body'),
		$styles = $('link[rel=stylesheet]'),
		$switchers_width,
		$switchers_bg,
		$switchers_scheme;

	$b.append('<div class="pi-settings-panel"><ul class="pi-settings-width"><li>Boxed</li><li class="pi-active">Wide</li></ul><h5>Background</h5><ul class="pi-settings-bg"><li class="pi-settings-btn-bg-wood" data-class="pi-bg-main-wood"></li><li class="pi-settings-btn-bg-wood-dark" data-class="pi-bg-main-wood-dark"></li><li class="pi-settings-btn-bg-wood-light" data-class="pi-bg-main-wood-light"></li><li class="pi-settings-btn-bg-embossed-dark" data-class="pi-bg-main-embossed-dark"></li></ul><h5>Color Scheme</h5><ul class="pi-settings-scheme"><li class="pi-settings-btn-scheme-base pi-active" data-color="base"></li><li class="pi-settings-btn-scheme-blue" data-color="blue"></li><li class="pi-settings-btn-scheme-green" data-color="green"></li><li class="pi-settings-btn-scheme-lime" data-color="lime"></li><li class="pi-settings-btn-scheme-orange" data-color="orange"></li><li class="pi-settings-btn-scheme-red" data-color="red"></li><li class="pi-settings-btn-scheme-brown" data-color="brown"></li><li class="pi-settings-btn-scheme-purple" data-color="purple"></li></ul><div class="pi-btn-settings"></div></div>');

	$switchers_width = $('.pi-settings-width li'),
	$switchers_bg = $('.pi-settings-bg li'),
	$switchers_scheme = $('.pi-settings-scheme li');

	//region Settings
	$('.pi-btn-settings').on('click', function() {
		$(this).parent().toggleClass('pi-active');
	});

	$switchers_width.on('click', function() {
		var $el = $(this),
			$parent = $el.parent(),
			isActive = $el.hasClass(cls_active);

		if(!isActive){
			$parent.find('li.' + cls_active).removeClass(cls_active);
			$el.addClass(cls_active);
			$b.toggleClass(cls_fixed);
		}

		if($b.hasClass(cls_fixed) && !currentBackground){
			$switchers_bg.eq(0).click();
		}

		$w.resize();

	});

	$switchers_bg.on('click', function() {
		var $el = $(this),
			$parent = $el.parent(),
			isActive = $el.hasClass(cls_active);

		if(!isActive){

			$parent.find('li.' + cls_active).removeClass(cls_active);
			$el.addClass(cls_active);

			var cl = $el.data('class');
			$b.addClass(cl).removeClass(currentBackground);
			currentBackground = cl;

		}

	});

	$switchers_scheme.on('click', function() {
		var $el = $(this),
			$parent = $el.parent(),
			isActive = $el.hasClass(cls_active);

		if(!isActive){

			$parent.find('li.' + cls_active).removeClass(cls_active);
			$el.addClass(cls_active);

			var cl = $el.data('color');

			if(currentColor != cl){
				currentColor = cl;

				if(isPreviewHost){

					var switchColorString = currentColor == 'base' ? '' : currentColor + '/',
						rpg = new RegExp(hostName + '\/' + '((blue|green|lime|brown|orange|red|purple)\/)?');

					window.location.href = pageUrl.replace(rpg, hostName + '/' + switchColorString);

				} else if(isBuild) {

					var switchColorString = currentColor == 'base' ? '' : currentColor + '/',
						rpg = new RegExp('build' + '\/' + '((blue|green|lime|brown|orange|red|purple)\/)?');

					window.location.href = pageUrl.replace(rpg, 'build/' + switchColorString);

				} else {

					$styles.each(function(){
						var $style = $(this),
							src = $style.attr('href');
						if(src.match(/\/css\/(base|blue|green|lime|brown|orange|red|purple)\//)){
							src = src.replace(/(\/css\/(base|blue|green|lime|brown|orange|red|purple)\/)/, '/css/' + cl + '/');
							$style.attr('href', src);
						}
					});

				}

			}
		}

	});


	if(currentColor != 'base'){
		$switchers_scheme.filter('[data-color=' + currentColor + ']').click();
	}

	//endregion

});