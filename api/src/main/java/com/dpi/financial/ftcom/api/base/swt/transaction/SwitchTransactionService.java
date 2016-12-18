package com.dpi.financial.ftcom.api.base.swt.transaction;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;

import java.util.Date;
import java.util.List;

public interface SwitchTransactionService extends GeneralServiceApi<SwitchTransaction> {
    public List<SwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber);
}
