package com.dpi.financial.ftcom.web.controller.base.simulator.message.type;


import com.dpi.financial.ftcom.web.controller.base.simulator.message.base.FundTransferBase;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.example.MessageHelper;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.IFundTransferCr;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.ITransaction;
import com.dpi.financial.ftcom.web.controller.base.simulator.utils.IranSystem;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class FundTransferCr extends FundTransferBase implements IFundTransferCr {
    private FundTransferCr(ProcessingCode processingCode) {
        super(processingCode);
    }

    public FundTransferCr() {
        this(ProcessingCode.FundTransferCr);
    }

    @Override
    public ITransaction createResponse() throws ISOException {
        if (!isRequest())
            throw new InvalidOperationException("Failed to create response");
        // super.createResponse();

        // create response transaction
        ITransaction response = null;
        try {
            response = new FundTransferCr();

            ISOMsg resMsg = (ISOMsg) isoMsg.clone();
            resMsg.setResponseMTI();
            resMsg.unset(52);
            resMsg.unset(48);
            resMsg.unset(48);
            resMsg.set(43, new IranSystem(MessageHelper.getDefaultFundTransferCr().getCardAcceptorNameLocation()).getBytes());
            resMsg.set(39, "00");

            if (resMsg.getMTI().equals("0210")) {
                Thread.sleep(100);
                resMsg.set(39, "80");
            } else if (resMsg.getMTI().equals("0410")) {
                Thread.sleep(100);
                resMsg.set(39, "00");
            }

            response.setISOMsg(resMsg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public ISOMsg createRequest(MessageDetails messageDetails) throws Exception{
        return isoMsg;
    }
}
