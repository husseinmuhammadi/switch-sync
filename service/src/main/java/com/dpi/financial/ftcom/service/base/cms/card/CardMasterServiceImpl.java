package com.dpi.financial.ftcom.service.base.cms.card;

import com.dpi.financial.ftcom.api.base.cms.card.CardMasterService;
import com.dpi.financial.ftcom.model.dao.cms.card.CardMasterDao;
import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(CardMasterService.class)
public class CardMasterServiceImpl implements CardMasterService {

    @EJB
    private CardMasterDao dao;

    @Override
    public CardMaster create(CardMaster cardMaster) {
        return dao.create(cardMaster);
    }

    @Override
    public List<CardMaster> findAll() {
        return dao.findAll();
    }

    @Override
    public CardMaster find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(CardMaster cardMaster) {
        dao.update(cardMaster);
    }

    @Override
    public void delete(CardMaster cardMaster) {
        dao.remove(cardMaster);
    }
}
