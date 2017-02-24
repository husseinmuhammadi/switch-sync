package com.dpi.financial.ftcom.service.base.meb.atm.transaction;

import com.dpi.financial.ftcom.api.base.meb.atm.transaction.SwipeCardService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.SwipeCardDao;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(SwipeCardService.class)
public class SwipeCardServiceImpl extends GeneralServiceImpl<SwipeCard>
        implements SwipeCardService {

    @EJB
    private SwipeCardDao dao;

    @Override
    public GenericDao<SwipeCard> getGenericDao() {
        return dao;
    }

}
