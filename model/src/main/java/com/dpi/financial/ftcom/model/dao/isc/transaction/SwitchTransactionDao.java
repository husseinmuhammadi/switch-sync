package com.dpi.financial.ftcom.model.dao.isc.transaction;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.isc.transaction.SwitchTransaction;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SwitchTransactionDao extends GenericDao<SwitchTransaction> {
    public SwitchTransactionDao() {
        super(SwitchTransaction.class);
    }

    public List<SwitchTransaction> findAll() {
        return createNamedQuery(SwitchTransaction.FIND_ALL).getResultList();
    }
}
