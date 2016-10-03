package com.dpi.financial.ftcom.web.controller.base.atm.terminal;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class TerminalController extends ControllerBase<Terminal>
        implements Serializable {

    @EJB
    private TerminalService terminalService;

    public TerminalController() {
        super(Terminal.class);
    }

    public Terminal getTerminal() {
        return super.getEntity();
    }

    public void setTerminal(Terminal terminal) {
        super.setEntity(terminal);
    }

    @Override
    public GeneralServiceApi<Terminal> getGeneralServiceApi() {
        return terminalService;
    }

    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }

    @Override
    public void prepare() {
        super.prepare();
        entity.setLuno(entity.getTerminalId());
    }

}
