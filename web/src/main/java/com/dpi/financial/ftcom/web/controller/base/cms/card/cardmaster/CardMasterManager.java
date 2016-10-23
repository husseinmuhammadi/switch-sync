package com.dpi.financial.ftcom.web.controller.base.cms.card.cardmaster;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.api.base.cms.card.CardMasterService;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

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
