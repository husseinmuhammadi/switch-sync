package com.dpi.financial.ftcom.model.dao.cms.card;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class MagneticStripeDao extends GenericDao<MagneticStripe> {
    public MagneticStripeDao() {
        super(MagneticStripe.class);
    }

    public List<MagneticStripe> findAll() {
        return null;
    }
}
