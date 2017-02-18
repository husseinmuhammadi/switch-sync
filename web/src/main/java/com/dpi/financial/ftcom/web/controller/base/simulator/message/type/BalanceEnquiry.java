package com.dpi.financial.ftcom.web.controller.base.simulator.message.type;

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

        Date d=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String dat = sdf.format(d);
        SimpleDateFormat sdf1 = new SimpleDateFormat("mmssSSS");
        String dat1 = sdf1.format(d);
        long stan = 200007;
        String rrn = "531111";
        stan = Integer.parseInt(dat1.substring(0, 6));
        System.out.println("stan : " + stan);
        rrn = 	"53" + dat.substring(0, 4) + dat1.substring(0, 6);
        stan = stan + 1000;


        //Updated by bhalchandra wrt Issue#5156 on 24-Apr-2014
        String cvv2 = messageDetails.getCvv2();
        String lang = "00";// 01:english 00:farsi
        String processCode = "";

        if(messageDetails.getFld3() != null) {
            processCode = messageDetails.getFld3().substring(0, 2);
        }

        ISOMsg isoMsg=new ISOMsg();


        String rrn1 = rrn ;
        System.out.println("RRN = " + rrn1);

        messageDetails.setPan("5859471010067944");
        messageDetails.setMti("0100");
        isoMsg.setMTI("0100");
        isoMsg.set(2,"5859471010067944");
        isoMsg.set(3,"310000");
        isoMsg.set(7,dat);
        isoMsg.set(11,(new Long(stan)).toString()); // increment stan
        isoMsg.set(12, ISODate.getTime(d));
        isoMsg.set(13,ISODate.getDate(d));
        isoMsg.set(14,"9903");
        isoMsg.set(17, ISODate.getDate(d));
        isoMsg.set(18,"6012");
        isoMsg.set(19,"364");
        isoMsg.set(22,"27");
        isoMsg.set(25, "02");
        isoMsg.set(26, "04");
        isoMsg.set(32, "610433");
        //isoMsg.set(35,"5859471010067944=2005101890000000000");
        isoMsg.set(62,"020000000108120000000000");
        isoMsg.set(33, "95001");
        isoMsg.set(41,"10006");
        isoMsg.set(42,"000000000582431");
        isoMsg.set(43,new IranSystem("SHEPAH BANK           TEHRAN       THRIR").getBytes());
        isoMsg.set(37,rrn1);
       isoMsg.set(48, getAdditionalData(messageDetails));
        if(messageDetails.getPin() != null) {
            isoMsg.set(52, ISOUtil.hex2byte(messageDetails.getPin()));
        }
        //isoMsg.set(95,"000000000000000000000000C00000000C00000000");

        messageDetails.setTrack2("5859471010067944=2005101890000000000");
        long keyIndex = ((stan % 2) + 1)+ 1;
        if (keyIndex == 0)
            keyIndex = 2;
        String keyIndexStr = keyIndex + "";
        while (keyIndexStr.length() < 2)
            keyIndexStr = "0" + keyIndexStr;

        String[] csdKey={"1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C",""};
        String track2 = Mac.instance.encTrack2(ISOUtil.hex2byte(csdKey[0]), messageDetails.getTrack2(), csdKey[1].equals("1"));
        isoMsg.set(35,track2);

        String[] pinKey = getKey("02", keyIndex);
        byte[] pinBlock = Mac.instance.pinEnc(messageDetails.getPin(), messageDetails.getPan(), ISOUtil.hex2byte(pinKey[0]), pinKey[1].equals("1"));

        byte[] pinBit60 = new byte[8];
        byte[] bit52 = new byte[8];
        System.arraycopy(pinBlock, 0, bit52, 0, 8);
        if (pinBlock.length > 8)
            System.arraycopy(pinBlock, 8, pinBit60, 0, 8);
        isoMsg.set(52, bit52);

        isoMsg.set(53,"0202010302000000");
        isoMsg.set(59, "00000000000000000000TEST123TEST");



        if(messageDetails.getAdditionalData() != null) {
            isoMsg.set(62, messageDetails.getAdditionalData());
        }

        //m.set(128, ISOUtil.hex2byte("2750243D43A12A00"));
        isoMsg.set(64, ISOUtil.hex2byte("2750243D43A12A00"));
        return isoMsg;
    }


    //mostafa
    private String[] getKey(String type, long index) {
        String key = "";
        String aes = "";
        if (type.equals("02")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
        }
        if (type.equals("03")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
        }
        if (type.equals("04")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
            aes = "1";
        }
        if (type.equals("05")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
            aes = "1";
        }
        if (type.equals("06")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
            aes = "1";
        }
        return new String[]{key, aes};
    }

    //get Field 48
    private String getAdditionalData(MessageDetails messageDetails){


        String field48="";
        String reserve="      ";// 6 char
        String lang = "00";// 01:english 00:farsi
        field48=reserve+lang;
        field48 +=messageDetails.getPan();
        return field48;

    }

}

