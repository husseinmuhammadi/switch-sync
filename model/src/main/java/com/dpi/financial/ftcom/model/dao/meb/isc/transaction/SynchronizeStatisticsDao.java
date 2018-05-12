package com.dpi.financial.ftcom.model.dao.meb.isc.transaction;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SynchronizeStatistics;
import com.dpi.financial.ftcom.model.type.ProcessingCode;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless(name = "MiddleEastBankSynchronizeStatisticsDao")
public class SynchronizeStatisticsDao extends GenericDao<SynchronizeStatistics> {
    public SynchronizeStatisticsDao() {
        super(SynchronizeStatistics.class);
    }

    public List<SynchronizeStatistics> findAll() {
        return createNamedQuery(SynchronizeStatistics.FIND_ALL).getResultList();
    }

    public List<SynchronizeStatistics> findAllCashWithdrawalByLuno(String luno) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        // parameters.put("processingCode", ProcessingCode.CASH_WITHDRAWAL);
        List<SynchronizeStatistics> switchTransactions = createNamedQuery(SynchronizeStatistics.FIND_BY_LUNO_PROCESSING_CODE, parameters).getResultList();
        return switchTransactions;
    }

    public SynchronizeStatistics findCashWithdrawalByLunoCardNumber(String luno, String cardNumber) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("cardNumber", cardNumber);
        parameters.put("processingCode", ProcessingCode.CASH_WITHDRAWAL);
        return createNamedQuery(SynchronizeStatistics.FIND_BY_LUNO_CARD_NUMBER_PROCESSING_CODE, parameters).getSingleResult();
    }
}
