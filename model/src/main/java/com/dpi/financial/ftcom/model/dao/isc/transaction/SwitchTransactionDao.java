package com.dpi.financial.ftcom.model.dao.isc.transaction;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.isc.transaction.FinancialBase;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SwitchTransactionDao extends GenericDao<FinancialBase> {
    public SwitchTransactionDao() {
        super(FinancialBase.class);
    }

    public List<FinancialBase> findAll() {
        return createNamedQuery(FinancialBase.FIND_ALL).getResultList();
    }
}
