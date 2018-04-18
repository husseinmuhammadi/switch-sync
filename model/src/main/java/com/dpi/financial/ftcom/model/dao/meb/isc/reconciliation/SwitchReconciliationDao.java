package com.dpi.financial.ftcom.model.dao.meb.isc.reconciliation;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SwitchReconciliationDao extends GenericDao<MiddleEastBankSwitchTransaction> {
    Logger logger = LoggerFactory.getLogger(SwitchReconciliationDao.class);

    public SwitchReconciliationDao() {
        super(MiddleEastBankSwitchTransaction.class);
    }

    public List<MiddleEastBankSwitchTransaction> findAll() {
        return createNamedQuery(MiddleEastBankSwitchTransaction.FIND_ALL).getResultList();
    }

    public List<String> findAllCard(String luno, Date transactionDateFrom, Date transactionDateTo) {
        logger.info("Finding all card to synchronizing for luno: {}", luno);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("transactionDateFrom", DateUtil.removeTime(transactionDateFrom));
        parameters.put("transactionDateTo", DateUtil.removeTime(transactionDateTo));

        TypedQuery<String> query = getEntityManager().createNamedQuery(MiddleEastBankSwitchTransaction.FIND_ALL_UNBOUND, String.class);

        query.setParameter("luno", luno);
        query.setParameter("transactionDateFrom", DateUtil.removeTime(transactionDateFrom));
        query.setParameter("transactionDateTo", DateUtil.removeTime(transactionDateTo));

        List<String> list = query.getResultList();
        logger.info("{} card to synchronizing found.", list.size());
        return list;
    }

    public List<MiddleEastBankSwitchTransaction> findInconsistentTransactions(String luno, Date switchTransactionDateFrom, Date switchTransactionDateTo) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("transactionDateFrom", DateUtil.removeTime(switchTransactionDateFrom));
        parameters.put("transactionDateTo", DateUtil.removeTime(switchTransactionDateTo));

        List<MiddleEastBankSwitchTransaction> switchTransactions = createNamedQuery(MiddleEastBankSwitchTransaction.FIND_INCONSISTENT_TRANSACTIONS, parameters).getResultList();

        for (MiddleEastBankSwitchTransaction switchTransaction : switchTransactions)
            Hibernate.initialize(switchTransaction.getTerminalTransaction());

        return switchTransactions;
    }

    public void syncByRetrievalReferenceNumber(String luno) {
        getEntityManager().createQuery("");
    }
}
