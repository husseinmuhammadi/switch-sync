package com.dpi.financial.ftcom.web.controller.base.simulator.message.type;


import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.ITransaction;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.base.AuthorizationBase;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;


public class CustomerEnquiry extends AuthorizationBase {

    public CustomerEnquiry() {
        super(ProcessingCode.CustomerEnquiry);
    }

    @Override
    public ITransaction createResponse() throws ISOException {
        if (!isRequest()) {
            throw new InvalidOperationException("Failed to create response");
        }

        ITransaction response = new CustomerEnquiry();
        ISOMsg resMsg = (ISOMsg) isoMsg.clone();
        resMsg.unset(52);

        /*
        if (isoMsg.getMTI().equalsIgnoreCase("0100")) {
            resMsg.setMTI("0110");
        }
        */

        resMsg.setResponseMTI();

        String name = "                         " + "0000";
        resMsg.set(44, name);
        resMsg.unset(48);
        resMsg.unset(42);
        resMsg.unset(43);
        resMsg.unset(4);
        resMsg.unset(6);
        resMsg.unset(10);
        resMsg.unset(25);
        resMsg.unset(49);
        resMsg.unset(51);
        resMsg.unset(17);
        resMsg.set(15, "1031");
        resMsg.set(38, "      ");
        resMsg.set(100, "622106");
        resMsg.set(102, "80000345982003");
        resMsg.set(128, ISOUtil.hex2byte("511321337C537AAD"));
        resMsg.set(39, "00");

        response.setISOMsg(resMsg);
        return response;
    }

    public ISOMsg createRequest(MessageDetails messageDetails) throws Exception{
        return isoMsg;
    }
}

