package com.dpi.financial.ftcom.web.controller.base.simulator.beans;


import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.SimulatorService;
import com.dpi.financial.ftcom.model.to.Simulator;
import com.dpi.financial.ftcom.web.controller.base.ControllerBase;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class SimulatorController extends ControllerBase<Simulator>
        implements Serializable {

    @EJB
    private SimulatorService simulatorService ;

    public SimulatorController() {super(Simulator.class);

    }



    public Simulator getSimulator() {return super.getEntity();}

    public void setSimulator(Simulator simulator) {super.setEntity(simulator);}

    @Override
    public GeneralServiceApi<Simulator> getGeneralServiceApi() {
        return simulatorService;
    }


    @Override
    public void init() {

    }

    @Override
    public void afterLoad() {

    }



}
