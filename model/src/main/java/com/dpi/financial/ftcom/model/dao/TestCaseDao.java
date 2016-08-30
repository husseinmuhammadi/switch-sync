package com.dpi.financial.ftcom.model.dao;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.model.to.TestCase;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TestCaseDao extends GenericDao<TestCase> {
    public TestCaseDao() {
        super(TestCase.class);
    }

    public List<TestCase> findAll() {
        return null;
    }
}
