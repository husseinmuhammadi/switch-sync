package com.dpi.financial.ftcom.web.controller.base.testcase;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.TestCaseService;
import com.dpi.financial.ftcom.model.to.TestCase;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TestCaseManager extends ControllerManagerBase<TestCase> implements Serializable {

    @EJB
    private TestCaseService testCaseService;

    // private List<TestCase> testCases;

    public TestCaseManager() {
        super(TestCase.class);
    }

    @Override
    public GeneralServiceApi<TestCase> getGeneralServiceApi() {
        return testCaseService;
    }

    @Override
    protected void onLoad() {

    }

    public List<TestCase> getTestCases() {
        return entityList;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.entityList = testCases;
    }
}
