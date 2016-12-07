package com.dpi.financial.ftcom.web.controller.base.cms.card.magneticstripe;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.cms.card.MagneticStripeService;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MagneticStripeController extends ControllerBase<MagneticStripe>
        implements Serializable {

    @EJB
    private MagneticStripeService service;

    private MagneticStripe magneticStripe;

    public MagneticStripeController() {
        super(MagneticStripe.class);
    }

    public MagneticStripe getMagneticStripe() {
        return super.entity;
    }

    public void setMagneticStripe(MagneticStripe magneticStripe) {
        super.entity = magneticStripe;
    }

    @Override
    public GeneralServiceApi<MagneticStripe> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void init() {
    }

    @Override
    public void afterLoad() {
        if (entity != null && entity.getId() != null)
            return;

        try {
            if (getMagneticStripe().getPan() != null) {
                MagneticStripe magneticStripe = service.findByPan(getMagneticStripe());
                if (magneticStripe != null && magneticStripe.getId() != null)
                    entity = magneticStripe;
            }
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public void generateTrack2(AjaxBehaviorEvent event) throws AbortProcessingException {
        try {
            // Long insuredInsurancePlanId = Long.parseLong(((UIInput) event.getComponent()).getValue().toString());
            MagneticStripe magneticStripe = service.generateTrackData(getMagneticStripe().getPan());
        /*
        InsuredInsurancePlan insuredInsurancePlan = insuredInsurancePlanService.find(insuredInsurancePlanId);
        if (insuredInsurancePlan != null) {
            InsurancePlan insurancePlan = insuredInsurancePlan.getInsurancePlan();
            if (insurancePlan != null && insurancePlan.getId() != null)
                onInsurancePlanChanged(insurancePlan);
        }
        */
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }
}