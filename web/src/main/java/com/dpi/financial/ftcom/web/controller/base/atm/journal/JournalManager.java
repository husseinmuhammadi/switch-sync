package com.dpi.financial.ftcom.web.controller.base.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class JournalManager extends ControllerManagerBase<SwitchTestScenario> implements Serializable {

    @EJB
    private SwitchTestScenarioService switchTestScenarioService;

    private List<SwitchTestScenario> switchTestScenarioList;

    public JournalManager() {
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
