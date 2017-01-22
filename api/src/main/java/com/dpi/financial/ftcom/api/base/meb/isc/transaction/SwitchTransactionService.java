package com.dpi.financial.ftcom.api.base.meb.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;

import java.util.List;

public interface SwitchTransactionService extends GeneralServiceApi<MiddleEastBankSwitchTransaction> {
    public List<MiddleEastBankSwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber);
}
