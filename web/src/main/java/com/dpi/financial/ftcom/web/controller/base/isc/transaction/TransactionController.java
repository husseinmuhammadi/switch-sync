package com.dpi.financial.ftcom.web.controller.base.isc.transaction;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.isc.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TransactionController extends ControllerBase<SwitchTransaction> implements Serializable {

    @EJB
    private SwitchTransactionService service;

    // private SwitchTransaction switchTransaction;

    public TransactionController() {
        super(SwitchTransaction.class);
    }

    public SwitchTransaction getSwitchTransaction() {
        return super.getEntity();
    }

    public void setSwitchTransaction(SwitchTransaction switchTransaction) {
        super.setEntity(switchTransaction);
    }

    @Override
    public GeneralServiceApi<SwitchTransaction> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }
}
