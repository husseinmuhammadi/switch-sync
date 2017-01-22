package com.dpi.financial.ftcom.web.controller.base.isc.test.testcase;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.test.SwitchTestCaseService;
import com.dpi.financial.ftcom.model.to.isc.test.SwitchTestCase;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SwitchTestCaseController extends ControllerBase<SwitchTestCase> implements Serializable {
    private static final long serialVersionUID = -4262455280051344309L;

    // private SwitchTestCase switchTestCase;

    @EJB
    private SwitchTestCaseService service;

    public SwitchTestCaseController() {
        super(SwitchTestCase.class);
    }

    public SwitchTestCase getSwitchTestCase() {
        return super.getEntity();
    }

    public void setSwitchTestCase(SwitchTestCase switchTestCase) {
        super.setEntity(switchTestCase);
    }

    @Override
    public GeneralServiceApi<SwitchTestCase> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void afterLoad() {

    }
}
