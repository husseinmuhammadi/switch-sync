package com.dpi.financial.ftcom.web.controller.base.meb.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("MiddleEastBankJournalFileController")
@ViewScoped
public class JournalFileController extends ControllerBase<JournalFile>
        implements Serializable {

    private static final long serialVersionUID = 152430837964278431L;

    @EJB
    private JournalFileService journalFileService;

    public JournalFileController() {
        super(JournalFile.class);
    }

    public JournalFile getJournalFile() {
        return super.getEntity();
    }

    public void setJournalFile(JournalFile journalFile) {
        super.setEntity(journalFile);
    }

    @Override
    public GeneralServiceApi<JournalFile> getGeneralServiceApi() {
        return journalFileService;
    }

    @Override
    public void afterLoad() {
    }
}
