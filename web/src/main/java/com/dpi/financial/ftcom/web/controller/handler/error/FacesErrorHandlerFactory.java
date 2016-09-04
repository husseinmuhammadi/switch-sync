package com.dpi.financial.ftcom.web.controller.handler.error;

import javax.faces.application.ProjectStage;

/**
 * Created by h.mohammadi on 9/3/2016.
 */
public class FacesErrorHandlerFactory {

    private final ProjectStage projectStage;

    public FacesErrorHandlerFactory(ProjectStage projectStage) {
        this.projectStage = projectStage;
    }

    public IFacesErrorHandler getFacesErrorHandler() {

        IFacesErrorHandler facesErrorHandler = null;

        switch (projectStage){
            case Production:
                facesErrorHandler = new ProductionFacesErrorHandler();
                break;
            case SystemTest:
                facesErrorHandler = new SystemTestFacesErrorHandler();
                break;
            case UnitTest:
                facesErrorHandler = new UnitTestFacesErrorHandler();
                break;
            case Development:
                facesErrorHandler = new DevelopmentFacesErrorHandler();
                break;
        }

        return facesErrorHandler;
    }
}
