package com.dpi.financial.ftcom.core.iso.listener;


import com.dpi.financial.ftcom.core.iso.common.TransactionContext;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.util.Log;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

public class ShetabListner extends Log implements ISORequestListener,
        LogSource, Configurable {
    private Logger logger_;
    private String realm_;
    protected Space space_;
    protected String queue;
    protected long respTime;
    protected int expiryTime;

    private Configuration configuration_;

    public ShetabListner() {
        /////////// VisaApplication visaApplication = new VisaApplication();
    }

    public Logger getLogger() {
        return this.logger_;
    }

    public String getRealm() {
        return this.realm_;
    }

    public void setLogger(Logger logger, String realm) {
        this.logger_ = logger;
        this.realm_ = realm;
    }

    public void setConfiguration(Configuration configuration)
            throws ConfigurationException {
        info("Configuration");
        this.configuration_ = configuration;
        queue = configuration.get("queue", null);
        respTime = Long.valueOf(configuration.get("respTime", null));
        if (queue == null) {
            throw new ConfigurationException("Property not specified : queue");
        }
        space_ = SpaceFactory.getSpace(configuration.get("space", null));
        expiryTime = Integer.parseInt(configuration.get("expiry_time", "7000"));
    }

    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {
        info("ShetabListner -> process()");

        // MessageReceiver msgReceiver = new MessageReceiver();
        // msgReceiver.setChannelType(VisaConstant.CHANNEL_TYPE_VISA);
        TransactionContext transactionContext = new TransactionContext(expiryTime);
//        transactionContext.put(VisaConstant.TXN_SOURCE, isoSource);
//        transactionContext.put(VisaConstant.TXN_REQUEST, isoMsg);
//        transactionContext.put(VisaConstant.TXN_SHETAB_REQUEST, isoMsg);
//        transactionContext.put(VisaConstant.RESPONSE_TIME, respTime);
//        transactionContext.put(VisaConstant.MSG_RECEIVER, msgReceiver);
//        transactionContext.put(VisaConstant.IS_SHETAB, "Y");
        space_.out(queue, transactionContext);

	/*	File f = new File("d:\\incomingtestmsg.txt");
		FileOutputStream out;
		try {
			out = new FileOutputStream(f);
			out.write(isoMsg.pack());
		} catch (Exception e) {
			e.printStackTrace();
		}*/


        info("ShetabListner -> process1()");
        return true;
    }

}
