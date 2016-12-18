package com.dpi.financial.ftcom.model.dao.swt.reconciliation;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.utility.date.DateUtil;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SwitchReconciliationDao extends GenericDao<SwitchTransaction> {
    public SwitchReconciliationDao() {
        super(SwitchTransaction.class);
    }

    public List<SwitchTransaction> findAll() {
        return createNamedQuery(SwitchTransaction.FIND_ALL).getResultList();
    }

    public List<String> findAllCard(String luno, Date transactionDateFrom, Date transactionDateTo) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("transactionDateFrom", DateUtil.removeTime(transactionDateFrom));
        parameters.put("transactionDateTo", DateUtil.removeTime(transactionDateTo));

        TypedQuery<String> query = getEntityManager().createNamedQuery(SwitchTransaction.FIND_ALL_UNBOUND, String.class);

        query.setParameter("luno", luno);
        query.setParameter("transactionDateFrom", DateUtil.removeTime(transactionDateFrom));
        query.setParameter("transactionDateTo", DateUtil.removeTime(transactionDateTo));

        return query.getResultList();
    }
}
