package com.dpi.financial.ftcom.web.controller.base.atm.terminal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TerminalManager extends ControllerManagerBase<Terminal> implements Serializable {

    @EJB
    private TerminalService terminalService;

    private List<Terminal> terminalList;

    public TerminalManager() {
        super(Terminal.class);
    }

    @Override
    public GeneralServiceApi<Terminal> getGeneralServiceApi() {
        return terminalService;
    }

    //TODO: Make this method private
    @Override
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        // externalContext.getFlash().keep();
        // if ()

        try {
            terminalList = terminalService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;
    }
}
