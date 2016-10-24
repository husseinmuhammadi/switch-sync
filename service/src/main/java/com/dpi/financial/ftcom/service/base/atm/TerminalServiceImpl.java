package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.dao.atm.TerminalDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.utility.helper.DateHelper;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(TerminalService.class)
public class TerminalServiceImpl implements TerminalService {

    @EJB
    private TerminalDao dao;

    @Override
    public Terminal create(Terminal terminal) {
        return dao.create(terminal);
    }

    @Override
    public List<Terminal> findAll() {
        return dao.findAll();
    }

    @Override
    public Terminal find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(Terminal terminal) {
        dao.update(terminal);
    }

    @Override
    public void delete(Terminal terminal) {
        dao.remove(terminal);
    }

    @Override
    public Terminal findByLuno(String luno) {
        return dao.findByLuno(luno);
    }
}
