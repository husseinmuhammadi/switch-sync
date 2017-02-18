package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.text.MessageFormat;
import java.util.List;

@Stateless
public class TerminalDao extends GenericDao<Terminal> {
    Logger logger = LoggerFactory.getLogger(TerminalDao.class);

    public TerminalDao() {
        super(Terminal.class);
    }

    public List<Terminal> findAll() {
        return createNamedQuery(Terminal.FIND_ALL).getResultList();
    }

    public Terminal findByLuno(String luno) {
        logger.info(MessageFormat.format("Find terminal: {0}", luno));
        return createNamedQuery(Terminal.FIND_BY_LUNO).setParameter("luno", luno).getSingleResult();
    }
}
