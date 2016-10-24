package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.Terminal;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class JournalFileDao extends GenericDao<JournalFile> {
    public JournalFileDao() {
        super(JournalFile.class);
    }

    public List<JournalFile> findAll() {
        return createNamedQuery(JournalFile.FIND_ALL).getResultList();
    }

    public List<JournalFile> findAll(Terminal terminal) {
        TypedQuery<JournalFile> namedQuery = createNamedQuery(JournalFile.FIND_BY_TERMINAL);
        namedQuery.setParameter("terminal", terminal);
        return namedQuery.getResultList();
    }
}
