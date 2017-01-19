package com.dpi.financial.ftcom.service.base.isc.transaction;


import com.dpi.financial.ftcom.api.base.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.isc.transaction.SwitchTransactionDao;
import com.dpi.financial.ftcom.model.to.isc.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(SwitchTransactionService.class)
public class SwitchTransactionServiceImpl extends GeneralServiceImpl<SwitchTransaction>
        implements SwitchTransactionService {
    @EJB
    private SwitchTransactionDao dao;

    @Override
    public GenericDao<SwitchTransaction> getGenericDao() {
        return dao;
    }
}
