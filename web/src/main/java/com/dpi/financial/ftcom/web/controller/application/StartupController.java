package com.dpi.financial.ftcom.web.controller.application;

import com.dpi.financial.ftcom.web.controller.conf.SwitchConfiguration;
import org.omnifaces.cdi.Startup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by h.mohammadi on 11/19/2016.
 * http://stackoverflow.com/questions/23014776/replacing-managedbeaneager-true-in-jsf22
 *
 * @since OmniFaces 1.8, there's a CDI compatible @Eager which
 * not only works on @Named @ApplicationScoped, but also
 * on CDI's @SessionScoped and @RequestScoped and OmniFaces @ViewScoped.
 * See also the blog entry and the showcase example.
 *
 * You can use it either with @Eager @ApplicationScoped
 * or with @Startup, which is a stereotype for @Eager @ApplicationScoped
 */
@Named
@Startup
public class StartupController implements Serializable {

    @Inject
    private SwitchConfiguration configuration;

    /*
    public void init(@Observes PostConstructApplicationEvent event) {
        // init here
        System.out.println("StartupController init ...");
    }
    */

    @PostConstruct
    public void init() {
        System.out.println("StartupController init ...");
    }

    @PreDestroy
    public void destoy() {

    }
}