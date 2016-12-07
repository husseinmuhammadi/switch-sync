package com.dpi.financial.ftcom.api.base.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.JournalTransaction;
import com.dpi.financial.ftcom.model.to.atm.Terminal;

import java.io.IOException;
import java.util.List;

public interface JournalTransactionService extends GeneralServiceApi<JournalTransaction> {

    void prepareSwipeCard(String baseFolder, Terminal terminal) throws IOException;

}
