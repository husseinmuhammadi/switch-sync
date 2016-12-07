package com.dpi.financial.ftcom.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by h.mohammadi on 11/19/2016.
 */
@Deprecated
public class EnvironmentListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /*
        WebApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        try {
            List<SystemProcessSettingVO> systemProcessSettingList = ((CoreService) appContext.getBean("coreService")).getSystemProcessSettingList();
            servletContextEvent.getServletContext().setAttribute(Constants.SYSTEM_PROCESS_SETTING_LIST, systemProcessSettingList);

        } catch (CoreServiceException e) {
            logger.error(e.getMessage(), e);
        }
        */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
