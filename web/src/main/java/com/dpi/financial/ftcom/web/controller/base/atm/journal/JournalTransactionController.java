package com.dpi.financial.ftcom.web.controller.base.atm.journal;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.JournalTransactionService;
import com.dpi.financial.ftcom.model.to.atm.JournalTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class JournalTransactionController extends ControllerBase<JournalTransaction>
        implements Serializable {

    @EJB
    private JournalTransactionService service;

    public JournalTransactionController() {
        super(JournalTransaction.class);
    }

    public JournalTransaction getJournalTransaction() {
        return super.getEntity();
    }

    public void setJournalTransaction(JournalTransaction journalTransactionRequest) {
        super.setEntity(journalTransactionRequest);
    }

    @Override
    public GeneralServiceApi<JournalTransaction> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void afterLoad() {

    }
}
