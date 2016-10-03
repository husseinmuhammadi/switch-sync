package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.JournalTransactionRequestService;
import com.dpi.financial.ftcom.model.dao.atm.JournalTransactionRequestDao;
import com.dpi.financial.ftcom.model.to.atm.JournalTransactionRequest;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(JournalTransactionRequestService.class)
public class JournalTransactionRequestServiceImpl implements JournalTransactionRequestService {

    @EJB
    private JournalTransactionRequestDao dao;

    @Override
    public JournalTransactionRequest create(JournalTransactionRequest journalTransactionRequest) {
        return dao.create(journalTransactionRequest);
    }

    @Override
    public List<JournalTransactionRequest> findAll() {
        return dao.findAll();
    }

    @Override
    public JournalTransactionRequest find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(JournalTransactionRequest journalTransactionRequest) {
        dao.update(journalTransactionRequest);
    }

    @Override
    public void delete(JournalTransactionRequest journalTransactionRequest) {
        dao.remove(journalTransactionRequest);
    }
}
