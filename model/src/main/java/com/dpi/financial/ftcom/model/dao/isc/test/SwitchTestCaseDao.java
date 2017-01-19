package com.dpi.financial.ftcom.model.dao.isc.test;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.isc.test.SwitchTestCase;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SwitchTestCaseDao extends GenericDao<SwitchTestCase> {
    public SwitchTestCaseDao() {
        super(SwitchTestCase.class);
    }

    public List<SwitchTestCase> findAll() {
        return createNamedQuery(SwitchTestCase.FIND_ALL).getResultList();
    }
}
