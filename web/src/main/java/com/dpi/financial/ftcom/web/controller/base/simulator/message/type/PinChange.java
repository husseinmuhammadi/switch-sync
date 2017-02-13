package com.dpi.financial.ftcom.web.controller.base.simulator.message.type;


import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.base.FinancialBase;
import org.jpos.iso.ISOMsg;

public class PinChange extends FinancialBase {

    public PinChange() {
        super(ProcessingCode.PinChange);
    }

    public ISOMsg createRequest(MessageDetails messageDetails) throws Exception{
        return isoMsg;
    }

}

