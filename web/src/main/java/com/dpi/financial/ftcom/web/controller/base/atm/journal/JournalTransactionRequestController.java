package com.dpi.financial.ftcom.web.controller.base.atm.journal;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.JournalTransactionRequestService;
import com.dpi.financial.ftcom.model.to.atm.JournalTransactionRequest;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class JournalTransactionRequestController extends ControllerBase<JournalTransactionRequest>
        implements Serializable {

    @EJB
    private JournalTransactionRequestService service;

    public JournalTransactionRequestController() {
        super(JournalTransactionRequest.class);
    }

    public JournalTransactionRequest getJournalTransactionRequest() {
        return super.getEntity();
    }

    public void setJournalTransactionRequest(JournalTransactionRequest journalTransactionRequest) {
        super.setEntity(journalTransactionRequest);
    }

    @Override
    public GeneralServiceApi<JournalTransactionRequest> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void afterLoad() {

    }
}
