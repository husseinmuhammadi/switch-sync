package com.dpi.financial.ftcom.api.base;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.Simulator;

public interface SimulatorService extends GeneralServiceApi<Simulator> {


   public Simulator finByRrn(String rrn);

}
