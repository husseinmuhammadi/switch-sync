package com.dpi.financial.ftcom.api.base.meb.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalContent;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;

import java.util.List;

public interface JournalContentService extends GeneralServiceApi<JournalContent> {
    List<JournalContent> findAll(Terminal terminal);

    void saveContents(List<JournalContent> journalContents);
}
