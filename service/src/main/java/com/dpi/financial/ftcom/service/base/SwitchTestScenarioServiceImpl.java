package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.SwitchTestScenarioDao;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(SwitchTestScenarioService.class)
public class SwitchTestScenarioServiceImpl extends GeneralServiceImpl<SwitchTestScenario> 
        implements SwitchTestScenarioService {

    @EJB
    private SwitchTestScenarioDao dao;

    @Override
    public GenericDao<SwitchTestScenario> getGenericDao() {
        return dao;
    }

    /*
    @Override
    public SwitchTestScenario create(SwitchTestScenario switchTestScenario) {
        return dao.create(switchTestScenario);
    }

    @Override
    public List<SwitchTestScenario> findAll() {
        return dao.findAll();
    }

    @Override
    public SwitchTestScenario find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(SwitchTestScenario switchTestScenario) {
        dao.update(switchTestScenario);
    }

    @Override
    public void delete(SwitchTestScenario switchTestScenario) {
        dao.remove(switchTestScenario);
    }
    */
}
