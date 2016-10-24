package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.JournalTransaction;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JournalTransactionDao extends GenericDao<JournalTransaction> {
    public JournalTransactionDao() {
        super(JournalTransaction.class);
    }

    public List<JournalTransaction> findAll() {
        return createNamedQuery(JournalTransaction.FIND_ALL).getResultList();
    }
}
