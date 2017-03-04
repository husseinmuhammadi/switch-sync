package com.dpi.financial.ftcom.web.controller.base.simulator.message.type;

import com.dpi.financial.ftcom.web.controller.base.simulator.beans.SimulatorManagedBean;
import com.dpi.financial.ftcom.web.controller.base.simulator.field.CreateSpecialField;
import com.dpi.financial.ftcom.web.controller.base.simulator.field.Mac;
import com.dpi.financial.ftcom.web.controller.base.simulator.message.base.FinancialBase;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.ITransaction;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ProcessingCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.utils.IranSystem;

import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;


import org.jpos.iso.ISOUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BalanceEnquiry extends FinancialBase {

    public BalanceEnquiry() {
        super(ProcessingCode.BalanceEnquiry);
    }
    
    @Override
	public ITransaction createResponse() throws ISOException {
		if (!isRequest())
            throw new InvalidOperationException("Failed to create response");
		// super.createResponse();
		
		// create response transaction
        ITransaction response = null;
		try {
			response = new FundTransferDr();

            ISOMsg resMsg = (ISOMsg) isoMsg.clone();
            resMsg.setResponseMTI();
            resMsg.unset(52);
            // resMsg.set(54, "1001364C0000012345671002364C000001234567");
            resMsg.set(39, "00");            
            
            resMsg.set(54, "1001364C0000012345671002364C000001234567");
            
            if (resMsg.getMTI().equals("0210")) {
                Thread.sleep(100);
                resMsg.set(39, "00");
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



        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String strDate = sdf.format(date);
        Mac mac = new Mac();

        ISOMsg isoMsg=new ISOMsg();
        isoMsg.setMTI("0100");
        isoMsg.set(2,messageDetails.getPan());
        isoMsg.set(3,"310000");
        isoMsg.set(7,strDate);
        isoMsg.set(11,CreateSpecialField.getStan(date)); // increment stan
        isoMsg.set(12, ISODate.getTime(date));
        isoMsg.set(13,ISODate.getDate(date));
        isoMsg.set(14,messageDetails.getExpDate());
        isoMsg.set(17, ISODate.getDate(date));
        isoMsg.set(18,"6012");
        isoMsg.set(19,"364");
        isoMsg.set(22,CreateSpecialField.gePointServiceEntrymode(messageDetails));
        isoMsg.set(25, CreateSpecialField.getPointserviceConditionCode(messageDetails));
        isoMsg.set(26, CreateSpecialField.getPointserviceCaptureCode(messageDetails));
        isoMsg.set(32, "610433");
        isoMsg.set(33, "95001");
        isoMsg.set(35,CreateSpecialField.getTrack2(messageDetails));
        isoMsg.set(41,"10006");
        isoMsg.set(42,"000000000582431");
        isoMsg.set(43,new IranSystem("SHEPAH BANK           TEHRAN       THRIR").getBytes());
        isoMsg.set(37,CreateSpecialField.getRrn(date));
        isoMsg.set(48, CreateSpecialField.getAdditionalData(messageDetails));
        isoMsg.set(52, CreateSpecialField.personalIdenNumPartOne(messageDetails));
        isoMsg.set(53,"0202010302000000");
        isoMsg.set(59, "00000000000000000000TEST123TEST");
        isoMsg.set(62,CreateSpecialField.getTransactonCoding(messageDetails));
        isoMsg.set(64, new Mac().calcMacV7(isoMsg, ISOUtil.hex2byte("1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C")));

        //isoMsg.set(62,"020000000108120000000000");
       // isoMsg.set(64, ISOUtil.hex2byte("2750243D43A12A00"));

        messageDetails.setRrn(isoMsg.getString(37));

        return isoMsg;
    }






}

