package com.dpi.financial.ftcom.web.controller.base.swt.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SwitchTransactionController extends ControllerBase<TerminalTransaction>
        implements Serializable {

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
