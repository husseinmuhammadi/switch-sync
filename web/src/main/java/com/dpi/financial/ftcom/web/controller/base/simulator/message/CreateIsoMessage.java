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
		((LogSource) channel).setLogger(logger, "TestDb-channel");
		channel.connect();
		ISOMsg isoMsg = new ISOMsg();
		TransactionFactory factory = new TransactionFactory();
		//String processingCode="31";
		String processingCode=messageDetails.getProcessingCode();

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









}
