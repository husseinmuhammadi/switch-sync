package com.dpi.financial.ftcom.api.base.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;

import java.io.IOException;
import java.util.Date;

public interface TerminalTransactionService extends GeneralServiceApi<TerminalTransaction> {
    void prepareAtmTransactions(String baseFolder, Terminal terminal, Date journalDateFrom, Date journalDateTo) throws IOException;

    void synchronizeAtmTransactions(Date journalDateFrom, Date journalDateTo);
}
