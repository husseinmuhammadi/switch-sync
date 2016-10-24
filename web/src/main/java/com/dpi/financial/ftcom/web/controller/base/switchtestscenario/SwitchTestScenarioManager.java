package com.dpi.financial.ftcom.web.controller.base.switchtestscenario;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.api.base.TestCaseService;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;
import com.dpi.financial.ftcom.model.to.TestCase;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class SwitchTestScenarioManager extends ControllerManagerBase<SwitchTestScenario> implements Serializable {

    @EJB
    private SwitchTestScenarioService switchTestScenarioService;

    private List<SwitchTestScenario> switchTestScenarioList;

    public SwitchTestScenarioManager() {
        super(SwitchTestScenario.class);
    }

    @Override
    public GeneralServiceApi<SwitchTestScenario> getGeneralServiceApi() {
        return switchTestScenarioService;
    }

    //TODO: Make this method private
    @Override
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        // externalContext.getFlash().keep();
        // if ()

        try {
            switchTestScenarioList = switchTestScenarioService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public List<SwitchTestScenario> getSwitchTestScenarioList() {
        return switchTestScenarioList;
    }

    public void setSwitchTestScenarioList(List<SwitchTestScenario> switchTestScenarioList) {
        this.switchTestScenarioList = switchTestScenarioList;
    }
}
