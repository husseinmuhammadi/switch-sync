package com.dpi.financial.ftcom.web.listener;

import com.dpi.financial.ftcom.web.annotation.Eager;

import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EagerListener implements ServletContextListener {

    private static final AnnotationLiteral<Eager> EAGER_ANNOTATION = new AnnotationLiteral<Eager>() {
        private static final long serialVersionUID = 1L;
    };

    @Override
    public void contextInitialized(ServletContextEvent event) {
        CDI.current().select(EAGER_ANNOTATION).forEach(bean -> bean.toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

}