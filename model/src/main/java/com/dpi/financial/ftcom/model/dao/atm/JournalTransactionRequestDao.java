package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.JournalTransactionRequest;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JournalTransactionRequestDao extends GenericDao<JournalTransactionRequest> {
    public JournalTransactionRequestDao() {
        super(JournalTransactionRequest.class);
    }

    public List<JournalTransactionRequest> findAll() {
        return createNamedQuery(JournalTransactionRequest.FIND_ALL).getResultList();
    }
}
