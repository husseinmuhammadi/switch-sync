package com.dpi.financial.ftcom.model.dao.meb.isc.transaction;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless(name = "MiddleEastBankSwitchTransactionDao")
public class SwitchTransactionDao extends GenericDao<MiddleEastBankSwitchTransaction> {
    public SwitchTransactionDao() {
        super(MiddleEastBankSwitchTransaction.class);
    }

    public List<MiddleEastBankSwitchTransaction> findAll() {
        return createNamedQuery(MiddleEastBankSwitchTransaction.FIND_ALL).getResultList();
    }

    public List<MiddleEastBankSwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("cardNumber", cardNumber);
        List<MiddleEastBankSwitchTransaction> switchTransactions = createNamedQuery(MiddleEastBankSwitchTransaction.FIND_ALL_BY_LUNO_CARD_NUMBER, parameters).getResultList();
        switchTransactions.forEach(switchTransaction -> Hibernate.initialize(switchTransaction.getTerminalTransaction()));
        return switchTransactions;
    }
}
