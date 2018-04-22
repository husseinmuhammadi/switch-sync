package com.dpi.financial.ftcom.service.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.base.meb.isc.reconciliation.SynchronizeStatisticsService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.transaction.SynchronizeStatisticsDao;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SynchronizeStatistics;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(SynchronizeStatisticsService.class)
public class SynchronizeStatisticsServiceImpl extends GeneralServiceImpl<SynchronizeStatistics> implements SynchronizeStatisticsService {

    @EJB
    private SynchronizeStatisticsDao dao;

    @Override
    public GenericDao<SynchronizeStatistics> getGenericDao() {
        return dao;
    }

    @Override
    public List<SynchronizeStatistics> findAllCashWithdrawalByTerminal(String luno) {
        return dao.findAllCashWithdrawalByLuno(luno);
    }
}
