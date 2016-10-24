package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TerminalDao extends GenericDao<Terminal> {
    public TerminalDao() {
        super(Terminal.class);
    }

    public List<Terminal> findAll() {
        return createNamedQuery(Terminal.FIND_ALL).getResultList();
    }

    public Terminal findByLuno(String luno) {
        return createNamedQuery(Terminal.FIND_BY_LUNO).setParameter("luno", luno).getSingleResult();
    }
}
