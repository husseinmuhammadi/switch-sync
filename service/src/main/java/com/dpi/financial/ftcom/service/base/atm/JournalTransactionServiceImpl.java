package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.JournalTransactionService;
import com.dpi.financial.ftcom.model.dao.atm.JournalTransactionDao;
import com.dpi.financial.ftcom.model.to.atm.JournalTransaction;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(JournalTransactionService.class)
public class JournalTransactionServiceImpl implements JournalTransactionService {

    @EJB
    private JournalTransactionDao dao;

    @Override
    public JournalTransaction create(JournalTransaction journalTransaction) {
        return dao.create(journalTransaction);
    }

    @Override
    public List<JournalTransaction> findAll() {
        return dao.findAll();
    }

    @Override
    public JournalTransaction find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(JournalTransaction journalTransaction) {
        dao.update(journalTransaction);
    }

    @Override
    public void delete(JournalTransaction journalTransaction) {
        dao.remove(journalTransaction);
    }
}
