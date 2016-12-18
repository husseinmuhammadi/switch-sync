package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.SwitchTestScenarioService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.SwitchTestScenarioDao;
import com.dpi.financial.ftcom.model.to.SwitchTestScenario;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

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
}
