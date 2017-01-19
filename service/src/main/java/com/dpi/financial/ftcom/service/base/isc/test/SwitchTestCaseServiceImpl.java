package com.dpi.financial.ftcom.service.base.isc.test;

import com.dpi.financial.ftcom.api.base.isc.test.SwitchTestCaseService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.isc.test.SwitchTestCaseDao;
import com.dpi.financial.ftcom.model.to.isc.test.SwitchTestCase;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(SwitchTestCaseService.class)
public class SwitchTestCaseServiceImpl extends GeneralServiceImpl<SwitchTestCase> implements SwitchTestCaseService {
    @EJB
    private SwitchTestCaseDao dao;

    @Override
    public GenericDao<SwitchTestCase> getGenericDao() {
        return dao;
    }
}
