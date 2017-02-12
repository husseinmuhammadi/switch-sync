package com.dpi.financial.ftcom.web.controller.base.meb.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("MiddleEastBankSwitchTransactionManager")
@ViewScoped
public class SwitchTransactionManager extends ControllerManagerBase<TerminalTransaction>
        implements Serializable {

    private static final long serialVersionUID = 937311323537976839L;

    @EJB
    private TerminalTransactionService service;

    // private List<TerminalTransaction> terminalTransactionList;

    public SwitchTransactionManager() {
        super(TerminalTransaction.class);
    }

    @Override
    public GeneralServiceApi<TerminalTransaction> getGeneralServiceApi() {
        return service;
    }

    @Override
    protected void onLoad() {

    }

    public List<TerminalTransaction> getTerminalTransactionList() {
        return entityList;
    }

    public void setTerminalTransactionList(List<TerminalTransaction> terminalTransactionList) {
        this.entityList = terminalTransactionList;
    }
}
