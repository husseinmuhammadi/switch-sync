package com.dpi.financial.ftcom.web.controller.base.cms.card.magneticstripecard;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.cms.card.MagneticStripeCardService;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripeCard;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MagneticStripeCardManager extends ControllerManagerBase<MagneticStripeCard> implements Serializable {

    @EJB
    private MagneticStripeCardService service;

    private List<MagneticStripeCard> magneticStripeCardList;

    public MagneticStripeCardManager() {
        super(MagneticStripeCard.class);
    }

    @Override
    public GeneralServiceApi<MagneticStripeCard> getGeneralServiceApi() {
        return service;
    }

    public void onLoad() {
    }

    public List<MagneticStripeCard> getMagneticStripeCardList() {
        return entityList;
    }

    public void setMagneticStripeCardList(List<MagneticStripeCard> magneticStripeCardList) {
        this.entityList = magneticStripeCardList;
    }
}
