package com.dpi.financial.ftcom.service.base;

import com.dpi.financial.ftcom.api.base.ProductService;
import com.dpi.financial.ftcom.api.base.TestCaseService;
import com.dpi.financial.ftcom.model.dao.ProductDao;
import com.dpi.financial.ftcom.model.dao.TestCaseDao;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.model.to.TestCase;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(TestCaseService.class)
public class TestCaseServiceImpl implements TestCaseService {

    @EJB
    private TestCaseDao testCaseDao;

    @Override
    public TestCase create(TestCase testCase) {
        return testCaseDao.create(testCase);
    }

    @Override
    public List<TestCase> findAll() {
        return testCaseDao.findAll();
    }

    @Override
    public TestCase find(Long id) {
        return testCaseDao.findById(id);
    }

    @Override
    public void update(TestCase testCase) {
        testCaseDao.update(testCase);
    }

    @Override
    public void delete(TestCase testCase) {
        testCaseDao.remove(testCase);
    }
}
