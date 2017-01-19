package com.dpi.financial.ftcom.web.controller.base.meb.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

    private List<TerminalTransaction> terminalTransactionList;

    public SwitchTransactionManager() {
        super(TerminalTransaction.class);
    }

    @Override
    public GeneralServiceApi<TerminalTransaction> getGeneralServiceApi() {
        return service;
    }

    //TODO: Make this method private
    @Override
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        // externalContext.getFlash().keep();
        // if ()

        try {
            terminalTransactionList = service.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public List<TerminalTransaction> getTerminalTransactionList() {
        return terminalTransactionList;
    }

    public void setTerminalTransactionList(List<TerminalTransaction> terminalTransactionList) {
        this.terminalTransactionList = terminalTransactionList;
    }
}
