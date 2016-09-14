package com.dpi.financial.ftcom.web.controller.base.switchtestscenario;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SwitchTestScenarioController extends ControllerBase<SwitchTestScenario>
        implements Serializable {

    @EJB
    private SwitchTestScenarioService switchTestScenarioService;

    public SwitchTestScenarioController() {
        super(SwitchTestScenario.class);
    }

    public SwitchTestScenario getSwitchTestScenario() {
        return super.getEntity();
    }

    public void setSwitchTestScenario(SwitchTestScenario switchTestScenario) {
        super.setEntity(switchTestScenario);
    }

    @Override
    public GeneralServiceApi<SwitchTestScenario> getGeneralServiceApi() {
        return switchTestScenarioService;
    }
}
