package com.dpi.financial.ftcom.api.base.meb.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;

import java.util.Date;
import java.util.List;

public interface JournalFileService extends GeneralServiceApi<JournalFile> {
    List<JournalFile> findAll(Terminal terminal);

    List<JournalFile> getJournalFileList(String baseFolder, Terminal terminal);

    List<JournalFile> getJournalFileList(Terminal terminal, Date journalDateFrom, Date journalDateTo);
}
