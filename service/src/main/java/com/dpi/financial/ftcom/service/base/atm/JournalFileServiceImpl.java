package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.model.dao.atm.JournalFileDao;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(JournalFileService.class)
public class JournalFileServiceImpl implements JournalFileService {

    @EJB
    private JournalFileDao dao;

    @Override
    public JournalFile create(JournalFile journalFile) {
        return dao.create(journalFile);
    }

    @Override
    public List<JournalFile> findAll() {
        return dao.findAll();
    }

    @Override
    public JournalFile find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(JournalFile journalFile) {
        dao.update(journalFile);
    }

    @Override
    public void delete(JournalFile journalFile) {
        dao.remove(journalFile);
    }
}
