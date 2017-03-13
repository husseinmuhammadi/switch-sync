package com.dpi.financial.ftcom.web.controller.base.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.isc.transaction.FinancialBase;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SwitchTransactionController extends ControllerBase<FinancialBase> implements Serializable {

    private static final long serialVersionUID = -7451715345159292678L;

    @EJB
    private SwitchTransactionService service;

    // private FinancialBase financialBase;

    public SwitchTransactionController() {
        super(FinancialBase.class);
    }

    @Override
    public GeneralServiceApi<FinancialBase> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }

    public FinancialBase getFinancialBase() {
        return super.entity;
    }

    public void setFinancialBase(FinancialBase financialBase) {
        super.entity = financialBase;
    }
}
