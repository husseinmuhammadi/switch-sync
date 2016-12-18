package com.dpi.financial.ftcom.model.dao.swt.transaction;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SwitchTransactionDao extends GenericDao<SwitchTransaction> {
    public SwitchTransactionDao() {
        super(SwitchTransaction.class);
    }

    public List<SwitchTransaction> findAll() {
        return createNamedQuery(SwitchTransaction.FIND_ALL).getResultList();
    }

    public List<SwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("cardNumber", cardNumber);
        List<SwitchTransaction> switchTransactions = createNamedQuery(SwitchTransaction.FIND_ALL_BY_LUNO_CARD_NUMBER, parameters).getResultList();
        switchTransactions.forEach(switchTransaction -> Hibernate.initialize(switchTransaction.getTerminalTransaction()));
        return switchTransactions;
    }
}
