package com.dpi.financial.ftcom.model.dao.meb.atm.journal;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalContent;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class JournalContentDao extends GenericDao<JournalContent> {
    Logger logger = LoggerFactory.getLogger(JournalContentDao.class);

    public JournalContentDao() {
        super(JournalContent.class);
    }

    public List<JournalContent> findAll() {
        logger.info("find all journal file...");
        // return createNamedQuery(JournalContent.FIND_ALL).getResultList();
        return null;
    }

    public List<JournalContent> findAll(Terminal terminal) {
        TypedQuery<JournalContent> namedQuery = createNamedQuery(JournalContent.FIND_BY_TERMINAL);
        namedQuery.setParameter("terminal", terminal);
        return namedQuery.getResultList();
    }
}
