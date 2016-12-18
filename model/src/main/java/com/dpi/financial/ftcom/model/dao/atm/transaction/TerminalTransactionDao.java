package com.dpi.financial.ftcom.model.dao.atm.transaction;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;
import org.hibernate.Hibernate;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TerminalTransactionDao extends GenericDao<TerminalTransaction> {
    public TerminalTransactionDao() {
        super(TerminalTransaction.class);
    }

    public List<TerminalTransaction> findAll() {
        return createNamedQuery(TerminalTransaction.FIND_ALL).getResultList();
    }

    public List<TerminalTransaction> findAllByLunoCardNumber(String luno, String pan) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("luno", luno);
        parameters.put("pan", pan);
        List<TerminalTransaction> terminalTransactions = createNamedQuery(TerminalTransaction.FIND_ALL_BY_LUNO_CARD_NUMBER, parameters).getResultList();
        terminalTransactions.forEach(terminalTransaction -> Hibernate.initialize(terminalTransaction.getSwitchTransaction()));
        return terminalTransactions;
    }
}
