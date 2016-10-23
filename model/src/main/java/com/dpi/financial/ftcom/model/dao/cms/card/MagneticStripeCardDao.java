package com.dpi.financial.ftcom.model.dao.cms.card;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripeCard;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class MagneticStripeCardDao extends GenericDao<MagneticStripeCard> {
    public MagneticStripeCardDao() {
        super(MagneticStripeCard.class);
    }

    public List<MagneticStripeCard> findAll() {
        return null;
    }
}
