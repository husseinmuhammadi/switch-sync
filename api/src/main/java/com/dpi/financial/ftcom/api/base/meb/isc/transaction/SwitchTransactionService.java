package com.dpi.financial.ftcom.api.base.meb.isc.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.isc.transaction.SwitchTransaction;

import java.util.List;

public interface SwitchTransactionService extends GeneralServiceApi<SwitchTransaction> {
    public List<SwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber);
}
