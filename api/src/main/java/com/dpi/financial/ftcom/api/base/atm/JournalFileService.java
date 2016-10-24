package com.dpi.financial.ftcom.api.base.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.Terminal;

import java.text.ParseException;
import java.util.List;

public interface JournalFileService extends GeneralServiceApi<JournalFile> {
    List<JournalFile> findAll(Terminal terminal);
    List<JournalFile> getJournalFileList(String path);
}
