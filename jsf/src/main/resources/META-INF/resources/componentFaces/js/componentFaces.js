function printName(url, width, height, resizable, modal, position) {
    var dialog = $('<div id="dialog-message" title="Important information"></div>').load(url).dialog({
        width: width,
        height: height,
        resizable: resizable,
        modal: modal,
        autoOpen: false,
        position: position,
        buttons: {
            "Close": function () {
                $(this).dialog("close");
            }
        }
    });
    dialog.dialog('open');
    return false;
}

function openDialog(url) {
    BootstrapDialog.show({
        title: 'Dialog',
        message: function (dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);

            return $message;
        },
        closable: true,
        draggable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        data: {
            'pageToLoad': url
        }
    });
    return false;
}

function ajaxRequest(urlPage, targetId) {
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $('#' + targetId.toString()).html(data);
    });
}

