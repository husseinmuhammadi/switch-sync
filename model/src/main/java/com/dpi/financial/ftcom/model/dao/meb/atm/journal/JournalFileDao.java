package com.dpi.financial.ftcom.model.dao.meb.atm.journal;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JournalFileDao extends GenericDao<JournalFile> {
    Logger logger = LoggerFactory.getLogger(JournalFileDao.class);

    public JournalFileDao() {
        super(JournalFile.class);
    }

    public List<JournalFile> findAll() {
        logger.info("find all journal file...");
        // return createNamedQuery(JournalFile.FIND_ALL).getResultList();
        return null;
    }

    public List<JournalFile> findAll(Terminal terminal) {
        TypedQuery<JournalFile> namedQuery = createNamedQuery(JournalFile.FIND_BY_TERMINAL);
        namedQuery.setParameter("terminal", terminal);
        return namedQuery.getResultList();
    }
}
