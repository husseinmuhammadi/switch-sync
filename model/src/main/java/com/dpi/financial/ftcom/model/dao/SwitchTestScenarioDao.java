package com.dpi.financial.ftcom.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SwitchTestScenarioDao extends GenericDao<SwitchTestScenario> {
    public SwitchTestScenarioDao() {
        super(SwitchTestScenario.class);
    }

    public List<SwitchTestScenario> findAll() {
        return createNamedQuery(SwitchTestScenario.FIND_ALL).getResultList();
    }
}
