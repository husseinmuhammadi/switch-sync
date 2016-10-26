package com.dpi.financial.ftcom.service.base.cms.card;

import com.dpi.financial.ftcom.api.base.cms.card.MagneticStripeService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.cms.card.MagneticStripeDao;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(MagneticStripeService.class)
public class MagneticStripeServiceImpl extends GeneralServiceImpl<MagneticStripe>
        implements MagneticStripeService {

    @EJB
    private MagneticStripeDao dao;

    @Override
    public GenericDao<MagneticStripe> getGenericDao() {
        return dao;
    }

    /*
    @Override
    public MagneticStripe create(MagneticStripe magneticStripe) {
        return dao.create(magneticStripe);
    }

    @Override
    public List<MagneticStripe> findAll() {
        return dao.findAll();
    }

    @Override
    public MagneticStripe find(Long id) {
        return dao.findById(id);
    }

    @Override
    public void update(MagneticStripe magneticStripe) {
        dao.update(magneticStripe);
    }

    @Override
    public void delete(MagneticStripe magneticStripe) {
        dao.remove(magneticStripe);
    }
    */
}
