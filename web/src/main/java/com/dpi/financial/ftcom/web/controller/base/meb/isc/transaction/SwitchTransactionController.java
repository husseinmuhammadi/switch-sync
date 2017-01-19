package com.dpi.financial.ftcom.web.controller.base.meb.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("MiddleEastBankSwitchTransactionController")
@ViewScoped
public class SwitchTransactionController extends ControllerBase<TerminalTransaction>
        implements Serializable {

    private static final long serialVersionUID = -5333925125702056222L;

    @EJB
    private TerminalTransactionService service;

    public SwitchTransactionController() {
        super(TerminalTransaction.class);
    }

    public TerminalTransaction getTerminalTransaction() {
        return super.getEntity();
    }

    public void setTerminalTransaction(TerminalTransaction terminalTransaction) {
        super.setEntity(terminalTransaction);
    }

    @Override
    public GeneralServiceApi<TerminalTransaction> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void afterLoad() {

    }
}
