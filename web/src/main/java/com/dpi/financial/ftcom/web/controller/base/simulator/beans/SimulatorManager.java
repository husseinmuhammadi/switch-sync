package com.dpi.financial.ftcom.web.controller.base.simulator.beans;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.SimulatorService;
import com.dpi.financial.ftcom.model.to.Simulator;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SimulatorManager extends ControllerManagerBase<Simulator> implements Serializable {

    @EJB
    private SimulatorService simulatorService;

    public SimulatorManager() {
        super(Simulator.class);
    }

    //private List<Simulator> simulatorList;

    private Simulator simulator;

    @Override
    public GeneralServiceApi<Simulator> getGeneralServiceApi() {
        return simulatorService;
    }

    @Override
    protected void onLoad() {



    }


    public List<Simulator> getSimulatorList() {
        return entityList;
    }

    public void setSimulatorList(List<Simulator> simulatorList) {
        this.entityList = simulatorList;
    }


 /*   public void setJournalFileList(List<JournalFile> journalFileList) {
        this.entityList = journalFileList;
    }
*/
   /*  try {
        String luno = terminal.getLuno();
        if (luno == null)
            return;

        // String path = configuration.getJournalPath();
        setJournalFileList(service.findAll(terminalService.findByLuno(luno)));
    } catch (Exception e) {
        e.printStackTrace();
        printErrorMessage(e);
    }*/
}
