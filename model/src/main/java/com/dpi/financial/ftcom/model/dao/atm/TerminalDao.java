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
}
