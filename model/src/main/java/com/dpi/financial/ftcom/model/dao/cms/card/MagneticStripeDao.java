package com.dpi.financial.ftcom.model.dao.cms.card;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MagneticStripeDao extends GenericDao<MagneticStripe> {
    public MagneticStripeDao() {
        super(MagneticStripe.class);
    }

    public List<MagneticStripe> findAll() {
        return null;
    }

    public MagneticStripe findByPan(MagneticStripe magneticStripe) {
        TypedQuery<MagneticStripe> namedQuery = createNamedQuery(MagneticStripe.FIND_BY_PRIMARY_ACCOUNT_NUMBER);
        namedQuery.setParameter("pan", magneticStripe.getPan());
        return namedQuery.getSingleResult();
    }
}
