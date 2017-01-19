package com.dpi.financial.ftcom.web.controller.base.isc.test.testcase;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.test.SwitchTestCaseService;
import com.dpi.financial.ftcom.model.to.isc.test.SwitchTestCase;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SwitchTestCaseManager extends ControllerManagerBase<SwitchTestCase> implements Serializable {

    @EJB
    private SwitchTestCaseService service;

    private List<SwitchTestCase> switchTestCases;

    public SwitchTestCaseManager() {
        super(SwitchTestCase.class);
    }

    @Override
    public GeneralServiceApi<SwitchTestCase> getGeneralServiceApi() {
        return service;
    }

    @Override
    @PostConstruct
    public void init() {
        switchTestCases = service.findAll();
    }

    public List<SwitchTestCase> getSwitchTestCases() {
        return switchTestCases;
    }

    public void setSwitchTestCases(List<SwitchTestCase> switchTestCases) {
        this.switchTestCases = switchTestCases;
    }
}
