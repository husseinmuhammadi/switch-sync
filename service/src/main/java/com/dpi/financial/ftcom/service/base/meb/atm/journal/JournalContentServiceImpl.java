package com.dpi.financial.ftcom.service.base.meb.atm.journal;

import com.dpi.financial.ftcom.api.base.meb.atm.JournalContentService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.journal.JournalContentDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.journal.JournalFileDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalContent;
import com.dpi.financial.ftcom.model.type.atm.journal.JournalFileState;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.text.MessageFormat;
import java.util.List;

@Stateless
@Local(JournalContentService.class)
public class JournalContentServiceImpl extends GeneralServiceImpl<JournalContent>
        implements JournalContentService {

    Logger logger = LoggerFactory.getLogger(JournalContentServiceImpl.class);

    @EJB
    private JournalContentDao dao;

    @EJB
    private JournalFileDao journalFileDao;

    @Override
    public GenericDao<JournalContent> getGenericDao() {
        return dao;
    }

    @Override
    public List<JournalContent> findAll(Terminal terminal) {
        logger.info(MessageFormat.format("Find journal files for terminal {0}", terminal.getLuno()));
        return dao.findAll(terminal);
    }

    @Override
    public void saveContents(List<JournalContent> journalContents) {
        for (JournalContent content : journalContents) {
            logger.info(MessageFormat.format("Save journal line {0}/{1}/{2}", content.getLuno(), content.getName(), content.getLineNumber()));
            create(content);
        }

        logger.info(MessageFormat.format("Update journal file {0}", journalContents.get(0).getJournalFile().getId()));
        // journalContents.get(0).getJournalFile().setState(JournalFileState.PREPARED);
        journalFileDao.update(journalContents.get(0).getJournalFile());
    }
}
