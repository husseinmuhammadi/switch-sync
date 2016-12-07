package com.dpi.financial.ftcom.model.dao.atm;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.transaction.SwipeCard;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SwipeCardDao extends GenericDao<SwipeCard> {
    public SwipeCardDao() {
        super(SwipeCard.class);
    }

    public List<SwipeCard> findAll() {
        return createNamedQuery(SwipeCard.FIND_ALL).getResultList();
    }
}
