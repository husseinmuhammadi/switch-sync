package com.dpi.financial.ftcom.web.controller.base.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.api.base.atm.JournalTransactionService;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;
import com.dpi.financial.ftcom.model.to.atm.JournalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class JournalTransactionManager extends ControllerManagerBase<JournalTransaction>
        implements Serializable {

    @EJB
    private JournalTransactionService service;

    private List<JournalTransaction> journalTransactionList;

    public JournalTransactionManager() {
        super(JournalTransaction.class);
    }

    @Override
    public GeneralServiceApi<JournalTransaction> getGeneralServiceApi() {
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
            journalTransactionList = service.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public List<JournalTransaction> getJournalTransactionList() {
        return journalTransactionList;
    }

    public void setJournalTransactionList(List<JournalTransaction> journalTransactionList) {
        this.journalTransactionList = journalTransactionList;
    }
}
