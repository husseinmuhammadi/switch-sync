package com.dpi.financial.ftcom.web.controller.base.meb.atm.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("MiddleEastBankTerminalTransactionController")
@ViewScoped
public class TerminalTransactionController extends ControllerBase<TerminalTransaction>
        implements Serializable {

    private static final long serialVersionUID = -6889841185939785037L;

    @EJB
    private TerminalTransactionService service;

    public TerminalTransactionController() {
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
