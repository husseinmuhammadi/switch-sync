package com.dpi.financial.ftcom.api.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SynchronizeStatistics;

import java.util.List;

public interface SynchronizeStatisticsService extends GeneralServiceApi<SynchronizeStatistics> {
    List<SynchronizeStatistics> findAllCashWithdrawalByTerminal(String luno);
}
