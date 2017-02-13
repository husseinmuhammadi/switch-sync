package com.dpi.financial.ftcom.web.controller.base.simulator.message;




import com.dpi.financial.ftcom.web.controller.base.simulator.factory.TransactionFactory;


import com.dpi.financial.ftcom.web.controller.base.simulator.message.type.*;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.ITransaction;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;


public class CreateIsoMessage {

	public void CreateIsoMessage() {

       /* try {
        	mux=QMUX.getMUX("load-mux");
        	
        	channel = (ChannelAdaptor) NameRegistrar.get("loadtest");
        	System.out.println("MUX:"+channel.isConnected());

        } catch (Exception e) {
        	e.printStackTrace();
        }*/

	}



	public void IntitIsoMsg(MessageDetails messageDetails) throws Exception {
		Logger logger = new Logger();


		logger.addListener(new SimpleLogListener(System.out));
		ISOChannel channel = new ASCIIChannel(
				"172.20.35.243", 39190, new ISO87APackager()
		);
		((LogSource) channel).setLogger(logger, "test-channel");
		channel.connect();
		ISOMsg isoMsg = new ISOMsg();
		TransactionFactory factory = new TransactionFactory();
		String processingCode="31";

		ITransaction transaction = factory.getTransaction( processingCode);
	//if (transaction.isRequest()) {
			if (transaction instanceof CustomerEnquiry
					|| transaction instanceof BillPayment
					|| transaction instanceof BalanceEnquiry
					|| transaction instanceof FundTransfer
					|| transaction instanceof FundTransferDr
					|| transaction instanceof FundTransferCr
					|| transaction instanceof CashWithdrawal) {
				System.out.println("step 1");
				isoMsg=transaction.createRequest(messageDetails);
			}
		//	}




		channel.send(isoMsg);
		channel.disconnect();

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



	//get Field 62
	private String getTransactionCode(MessageDetails messageDetails)
	{
		String field62="";
	//	String terminalId=cardDetails.getCARD_ACCEPTOR_TERMINAL_ID();
		String errorIndicator="000";
		String reasonCode = "0000";

		//String bit62 = terminalId + errorIndicator + reasonCode + functionCode + secutiryConfig + shetabControllingData + tranSupplData + cardAccSupplData + panEnc + sourceCardEnc;
		return field62;
	}

}
