package com.en.std.message;

import com.en.std.definition.ProcessingCode;
import com.en.std.exception.InvalidOperationException;
import com.en.std.message.base.ITransaction;
import com.en.std.message.example.MessageHelper;
import com.en.std.utils.IranSystem;
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
}
