package com.dpi.financial.ftcom.web.controller.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("MiddleEastBankSwitchReconciliationController")
@ViewScoped
public class SwitchReconciliationController extends ControllerBase<SwitchTransaction>
        implements Serializable {

    private static final long serialVersionUID = -562453531781305084L;

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
