package com.dpi.financial.ftcom.web.controller.base.swt.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.swt.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SwitchReconciliationController extends ControllerBase<SwitchTransaction>
        implements Serializable {

    @EJB
    private SwitchTransactionService service;

    public SwitchReconciliationController() {
        super(SwitchTransaction.class);
    }

    public SwitchTransaction getSwitchTransaction() {
        return super.getEntity();
    }

    public void setSwitchTransaction(SwitchTransaction terminalTransaction) {
        super.setEntity(terminalTransaction);
    }

    @Override
    public GeneralServiceApi<SwitchTransaction> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void afterLoad() {

    }
}
