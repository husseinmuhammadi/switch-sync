package com.dpi.financial.ftcom.api.base.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.Terminal;

public interface TerminalService extends GeneralServiceApi<Terminal> {

    Terminal findByLuno(String luno);
}
