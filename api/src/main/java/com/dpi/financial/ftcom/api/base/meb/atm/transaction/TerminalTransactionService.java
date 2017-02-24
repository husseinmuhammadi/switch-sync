package com.dpi.financial.ftcom.api.base.meb.atm.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface TerminalTransactionService extends GeneralServiceApi<TerminalTransaction> {
    List<TerminalTransaction> findAllByLunoCardNumber(String luno, String cardNumber);

    void saveSwipeCardAndTransactions(SwipeCard swipeCard);
}
