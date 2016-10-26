package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.TestCaseService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.TestCaseDao;
import com.dpi.financial.ftcom.model.to.TestCase;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(TestCaseService.class)
public class TestCaseServiceImpl extends GeneralServiceImpl<TestCase> implements TestCaseService {

    @EJB
    private TestCaseDao dao;

    @Override
    public GenericDao<TestCase> getGenericDao() {
        return dao;
    }

    /*
    @Override
    public TestCase create(TestCase testCase) {
        return dao.create(testCase);
    }

    @Override
    public List<TestCase> findAll() {
        return dao.findAll();
    }

    @Override
    public TestCase find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(TestCase testCase) {
        dao.update(testCase);
    }

    @Override
    public void delete(TestCase testCase) {
        dao.remove(testCase);
    }
    */
}
