package com.dpi.financial.ftcom.web.controller.base.testcase;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.api.base.TestCaseService;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.model.to.TestCase;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TestCaseController extends ControllerBase<TestCase> implements Serializable {

    @EJB
    private TestCaseService testCaseService;

    public TestCaseController() {
        super(TestCase.class);
    }

    public TestCase getTestCase() {
        return super.getEntity();
    }

    public void setTestCase(TestCase testCase) {
        super.setEntity(testCase);
    }


    @Override
    public GeneralServiceApi<TestCase> getGeneralServiceApi() {
        return testCaseService;
    }

    @Override
    public void afterLoad() {

    }
}
