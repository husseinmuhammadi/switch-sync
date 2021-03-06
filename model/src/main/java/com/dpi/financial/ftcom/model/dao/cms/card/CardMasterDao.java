package com.dpi.financial.ftcom.model.dao.cms.card;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CardMasterDao extends GenericDao<CardMaster> {
    public CardMasterDao() {
        super(CardMaster.class);
    }

    public List<CardMaster> findAll() {
        return createNamedQuery(CardMaster.FIND_ALL).getResultList();
    }
}
