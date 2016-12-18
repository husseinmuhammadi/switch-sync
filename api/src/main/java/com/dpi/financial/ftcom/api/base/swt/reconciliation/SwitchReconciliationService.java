package com.dpi.financial.ftcom.api.base.swt.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;

import java.util.Date;
import java.util.List;

public interface SwitchReconciliationService extends GeneralServiceApi<SwitchTransaction> {
    void synchronizeAtmTransactions(String luno, Date transactionDateFrom, Date transactionDateTo);

    void synchronizeAtmTransactions(String luno, String cardNumber);

    List<String> findAllCard(String luno, Date transactionDateFrom, Date transactionDateTo);
}
