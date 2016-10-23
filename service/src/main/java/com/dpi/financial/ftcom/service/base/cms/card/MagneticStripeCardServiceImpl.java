package com.dpi.financial.ftcom.service.base.cms.card;

import com.dpi.financial.ftcom.api.base.cms.card.MagneticStripeCardService;
import com.dpi.financial.ftcom.model.dao.cms.card.MagneticStripeCardDao;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripeCard;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(MagneticStripeCardService.class)
public class MagneticStripeCardServiceImpl implements MagneticStripeCardService {

    @EJB
    private MagneticStripeCardDao dao;

    @Override
    public MagneticStripeCard create(MagneticStripeCard magneticStripeCard) {
        return dao.create(magneticStripeCard);
    }

    @Override
    public List<MagneticStripeCard> findAll() {
        return dao.findAll();
    }

    @Override
    public MagneticStripeCard find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(MagneticStripeCard magneticStripeCard) {
        dao.update(magneticStripeCard);
    }

    @Override
    public void delete(MagneticStripeCard magneticStripeCard) {
        dao.remove(magneticStripeCard);
    }
}
