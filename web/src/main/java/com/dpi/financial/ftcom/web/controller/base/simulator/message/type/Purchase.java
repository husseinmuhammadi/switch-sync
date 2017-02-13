package com.dpi.financial.ftcom.web.controller.base.simulator.message.type;


import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.base.FinancialBase;
import org.jpos.iso.ISOMsg;

public class Purchase extends FinancialBase {

    public Purchase() {
        super(ProcessingCode.Purchase);
    }

    public ISOMsg createRequest(MessageDetails messageDetails) throws Exception{
        return isoMsg;
    }
}

