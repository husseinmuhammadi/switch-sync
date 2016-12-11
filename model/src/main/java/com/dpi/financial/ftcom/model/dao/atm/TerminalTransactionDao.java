package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TerminalTransactionDao extends GenericDao<TerminalTransaction> {
    public TerminalTransactionDao() {
        super(TerminalTransaction.class);
    }

    public List<TerminalTransaction> findAll() {
        return createNamedQuery(TerminalTransaction.FIND_ALL).getResultList();
    }
}
