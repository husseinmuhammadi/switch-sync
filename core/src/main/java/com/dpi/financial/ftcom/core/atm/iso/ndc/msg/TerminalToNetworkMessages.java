package com.dpi.financial.ftcom.core.atm.iso.ndc.msg;

import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;

/**
 * Created by h.mohammadi on 12/29/2016.
 */
public abstract class TerminalToNetworkMessages extends NdcMsg {
    protected TerminalToNetworkMessages() {
        super();
    }

    public abstract MessageIdentifier getIdentifier();
}
