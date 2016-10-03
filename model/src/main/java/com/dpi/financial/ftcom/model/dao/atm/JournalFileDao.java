package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JournalFileDao extends GenericDao<JournalFile> {
    public JournalFileDao() {
        super(JournalFile.class);
    }

    public List<JournalFile> findAll() {
        return createNamedQuery(JournalFile.FIND_ALL).getResultList();
    }
}
