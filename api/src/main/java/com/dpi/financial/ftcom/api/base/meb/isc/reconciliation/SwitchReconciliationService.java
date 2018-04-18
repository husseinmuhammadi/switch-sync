package com.dpi.financial.ftcom.api.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;

import java.util.Date;
import java.util.List;

public interface SwitchReconciliationService extends GeneralServiceApi<MiddleEastBankSwitchTransaction> {
    void synchronizeAtmTransactions(String luno, String cardNumber);

    List<String> findAllCard(String luno, Date transactionDateFrom, Date transactionDateTo);

    List<MiddleEastBankSwitchTransaction> findInconsistentTransactions(String luno, Date switchTransactionDateFrom, Date switchTransactionDateTo);

    void syncByRetrievalReferenceNumber(String luno);
}
