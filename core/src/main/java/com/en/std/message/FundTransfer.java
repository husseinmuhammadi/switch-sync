package com.en.std.message;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.en.std.definition.ProcessingCode;
import com.en.std.exception.InvalidOperationException;
import com.en.std.message.base.FinancialBase;
import com.en.std.message.base.ITransaction;

public class FundTransfer extends FinancialBase {

    public FundTransfer() {
        super(ProcessingCode.FundTransfer);
    }
    
    @Override
	public ITransaction createResponse() throws ISOException {		
		if (!isRequest())
            throw new InvalidOperationException("Failed to create response");
		// super.createResponse();
		
		// create response transaction
        ITransaction response = null;
		try {
			response = new FundTransfer();

            ISOMsg resMsg = (ISOMsg) isoMsg.clone();
            resMsg.setResponseMTI();
            resMsg.unset(52);
            // resMsg.set(54, "1001364C0000012345671002364C000001234567");
            resMsg.set(39, "00");            
            
            if (resMsg.getMTI().equals("0210")) {
                Thread.sleep(100);
                resMsg.set(39, "80");
            } else if (resMsg.getMTI().equals("0410")) {
            	Thread.sleep(1000);
                resMsg.set(39, "00");
            }			
			
            response.setISOMsg(resMsg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
        return response;		
	}
}

