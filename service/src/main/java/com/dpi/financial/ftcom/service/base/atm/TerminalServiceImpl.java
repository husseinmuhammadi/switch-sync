package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.dao.atm.TerminalDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(TerminalService.class)
public class TerminalServiceImpl implements TerminalService {

    @EJB
    private TerminalDao terminalDao;

    @Override
    public Terminal create(Terminal terminal) {
        return terminalDao.create(terminal);
    }

    @Override
    public List<Terminal> findAll() {
        return terminalDao.findAll();
    }

    @Override
    public Terminal find(Long id) {
        return terminalDao.findById(id);
    }

    @Override
    public void update(Terminal terminal) {
        terminalDao.update(terminal);
    }

    @Override
    public void delete(Terminal terminal) {
        terminalDao.remove(terminal);
    }
}
