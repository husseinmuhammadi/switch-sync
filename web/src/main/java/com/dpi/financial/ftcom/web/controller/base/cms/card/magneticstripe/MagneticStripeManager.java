package com.dpi.financial.ftcom.web.controller.base.cms.card.magneticstripe;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.cms.card.MagneticStripeService;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MagneticStripeManager extends ControllerManagerBase<MagneticStripe>
        implements Serializable {

    @EJB
    private MagneticStripeService service;

    private List<MagneticStripe> magneticStripeList;

    public MagneticStripeManager() {
        super(MagneticStripe.class);
    }

    @Override
    public GeneralServiceApi<MagneticStripe> getGeneralServiceApi() {
        return service;
    }

    public void onLoad() {
    }

    public List<MagneticStripe> getMagneticStripeList() {
        return entityList;
    }

    public void setMagneticStripeList(List<MagneticStripe> magneticStripeList) {
        super.entityList = magneticStripeList;
    }
}
