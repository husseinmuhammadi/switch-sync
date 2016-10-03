package com.dpi.financial.ftcom.web.controller.base.atm.journal;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class JournalFileController extends ControllerBase<JournalFile>
        implements Serializable {

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
