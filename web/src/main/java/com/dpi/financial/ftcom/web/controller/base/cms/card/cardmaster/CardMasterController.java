package com.dpi.financial.ftcom.web.controller.base.cms.card.cardmaster;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.cms.card.CardMasterService;
import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CardMasterController extends ControllerBase<CardMaster>
        implements Serializable {

    @EJB
    private CardMasterService service;

    private CardMaster cardMaster;

    public CardMasterController() {
        super(CardMaster.class);
    }

    public CardMaster getCardMaster() {
        return super.entity;
    }

    public void setCardMaster(CardMaster cardMaster) {
        super.entity = cardMaster;
    }

    @Override
    public GeneralServiceApi<CardMaster> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }
}
