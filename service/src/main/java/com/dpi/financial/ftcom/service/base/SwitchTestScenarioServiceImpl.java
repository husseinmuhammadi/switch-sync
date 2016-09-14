package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.model.dao.SwitchTestScenarioDao;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(SwitchTestScenarioService.class)
public class SwitchTestScenarioServiceImpl implements SwitchTestScenarioService {

    @EJB
    private SwitchTestScenarioDao switchTestScenarioDao;

    @Override
    public SwitchTestScenario create(SwitchTestScenario switchTestScenario) {
        return switchTestScenarioDao.create(switchTestScenario);
    }

    @Override
    public List<SwitchTestScenario> findAll() {
        return switchTestScenarioDao.findAll();
    }

    @Override
    public SwitchTestScenario find(Long id) {
        return switchTestScenarioDao.findById(id);
    }

    @Override
    public void update(SwitchTestScenario switchTestScenario) {
        switchTestScenarioDao.update(switchTestScenario);
    }

    @Override
    public void delete(SwitchTestScenario switchTestScenario) {
        switchTestScenarioDao.remove(switchTestScenario);
    }
}
