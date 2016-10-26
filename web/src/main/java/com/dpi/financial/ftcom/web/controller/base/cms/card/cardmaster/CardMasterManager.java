package com.dpi.financial.ftcom.web.controller.base.cms.card.cardmaster;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.cms.card.CardMasterService;
import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CardMasterManager extends ControllerManagerBase<CardMaster> implements Serializable {

    @EJB
    private CardMasterService service;

    private List<CardMaster> cardMasterList;

    public CardMasterManager() {
        super(CardMaster.class);
    }

    @Override
    public GeneralServiceApi<CardMaster> getGeneralServiceApi() {
        return service;
    }

    public void onLoad() {
    }

    public List<CardMaster> getCardMasterList() {
        return entityList;
    }

    public void setCardMasterList(List<CardMaster> cardMasterList) {
        this.entityList = cardMasterList;
    }
}
