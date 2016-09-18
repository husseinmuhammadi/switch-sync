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

    private List<TestCase> testCaseList;

    public TestCaseManager() {
        super(TestCase.class);
    }

    @Override
    public GeneralServiceApi<TestCase> getGeneralServiceApi() {
        return testCaseService;
    }

    @Override
    @PostConstruct
    public void init() {
        testCaseList = testCaseService.findAll();
    }

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }
}
