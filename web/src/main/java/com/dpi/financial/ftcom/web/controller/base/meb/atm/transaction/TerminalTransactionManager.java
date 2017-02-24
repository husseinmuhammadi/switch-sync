package com.dpi.financial.ftcom.web.controller.base.meb.atm.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("MiddleEastBankTerminalTransactionManager")
@ViewScoped
public class TerminalTransactionManager extends ControllerManagerBase<TerminalTransaction>
        implements Serializable {

    private static final long serialVersionUID = -7435934351695583907L;
    Logger logger = LoggerFactory.getLogger(TerminalTransactionManager.class);

    @EJB
    private TerminalTransactionService service;

    // private List<TerminalTransaction> terminalTransactions;

    public TerminalTransactionManager() {
        super(TerminalTransaction.class);
    }

    @Override
    public GeneralServiceApi<TerminalTransaction> getGeneralServiceApi() {
        return service;
    }

    @Override
    protected void onLoad() {

    }

    public List<TerminalTransaction> getTerminalTransactions() {
        return entityList;
    }

    public void setTerminalTransactions(List<TerminalTransaction> terminalTransactions) {
        this.entityList = terminalTransactions;
    }
}
