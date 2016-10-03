package com.en.listener;

import java.util.concurrent.CountDownLatch;

import javax.activity.InvalidActivityException;
import javax.swing.JTextArea;

import com.en.std.exception.InvalidOperationException;
import com.en.std.message.CustomerEnquiry;
import com.en.std.message.base.ITransaction;
import com.en.std.message.context.TransactionContext;
import com.en.std.message.factory.TransactionFactory;
import com.en.std.simulate.SwitchEmulator;

import loadtest.LoadResponseListener;
import loadtest.MyListenerGenerator;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.MUX;
import org.jpos.q2.iso.QMUX;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.NameRegistrar;

import com.en.std.message.BalanceEnquiry;
import com.en.std.message.BillPayment;
import com.en.std.message.CashWithdrawal;
import com.en.std.message.FundTransfer;
import com.en.std.message.FundTransferCr;
import com.en.std.message.FundTransferDr;
import com.en.std.message.IFundTransferCr;
import com.en.std.message.definitions.ProcessPattern;
import com.en.std.message.example.MessageHelper;
import com.en.std.utils.HexUtils;
import com.en.std.utils.IranSystem;

public class LoadListener implements ISORequestListener,
        LogSource, Configurable {

    private Logger logger_;

    private String realm_;

    protected Space space_;

    protected String queue;

    protected long respTime;

    MyListenerGenerator l = null;

    private Configuration configuration_;

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
        System.out.println("Configuration");
        this.configuration_ = configuration;
        queue = configuration.get("queue", null);
        respTime = Long.valueOf(configuration.get("respTime", null));
        if (queue == null) {
            throw new ConfigurationException("Property not specified : queue");
        }
        space_ = SpaceFactory.getSpace(configuration.get("space", null));

    }

    public boolean process(ISOSource isoSource, ISOMsg isoMsg) {

        try {
            System.out.println("Recieved Shetab reply message Message ");
/*      if(l == null) {
        System.out.println("Recieved Login Message");
        l = new MyListenerGenerator(isoSource);
        l.setVisible(true);
      }
*/
            TransactionFactory factory = new TransactionFactory();

            MUX mux = null;

            if (!isoMsg.getMTI().equalsIgnoreCase("0810") && isoMsg.isResponse() == true) {
                JTextArea logArea = (JTextArea) NameRegistrar.getIfExists("txtarea");
                if (logArea != null) {
                    logArea.append("\nResponse Recieved for Transaction = " + isoMsg.getString("39"));
                }

                Object common = NameRegistrar.getIfExists("COMMON");
                if (common != null) {
                    NameRegistrar.register("RESPONSE", isoMsg);
                    synchronized (common) {
                        common.notifyAll();
                    }
                }
            }

            TransactionContext context = new TransactionContext();
            ITransaction transaction = factory.getTransaction(isoMsg);

            if (transaction.isRequest()) {
                if (transaction instanceof CustomerEnquiry
                		|| transaction instanceof BillPayment 
                		|| transaction instanceof BalanceEnquiry
                		|| transaction instanceof FundTransfer                		
                		|| transaction instanceof FundTransferDr
                		|| transaction instanceof FundTransferCr
                		|| transaction instanceof CashWithdrawal) {
                    context.request = transaction;

                    context.response = transaction.createResponse();

                    CountDownLatch doneSignal = new CountDownLatch(1);
                    LoadResponseListener listener = new LoadResponseListener(doneSignal, 1);
                    mux = QMUX.getMUX("load-mux");
                    if (!(transaction instanceof CustomerEnquiry)) {
                        Thread.sleep(respTime);
                    }
                    //mux.request(resMsg, 1000,listener, new Object());
                    Space space = SpaceFactory.getSpace();
                    space.out("load-send", context.response.getISOMsg());
                }
            }            
            
            if (isoMsg.isRequest() == true
                    && !(transaction instanceof CustomerEnquiry
                    		|| transaction instanceof BalanceEnquiry
                    		|| transaction instanceof BillPayment
                    		|| transaction instanceof FundTransfer    
                    		|| transaction instanceof FundTransferDr
                    		|| transaction instanceof FundTransferCr
                    		|| transaction instanceof CashWithdrawal)) {            	

                ISOMsg resMsg = (ISOMsg) isoMsg.clone();
                resMsg.unset(52);
                if (isoMsg.getMTI().equalsIgnoreCase("0200")) {
                    resMsg.setMTI("0210");
                } else if (isoMsg.getMTI().equalsIgnoreCase("0400")) {
                    resMsg.setMTI("0410");
                } else if (isoMsg.getMTI().equalsIgnoreCase("0420")) {
                    resMsg.setMTI("0430");
                }
                if (isoMsg.getMTI().equalsIgnoreCase("0800")) {
                    resMsg.setMTI("0810");
                }

                String processCode = resMsg.getString(3);
                if (processCode == null) {
                    processCode = "      ";
                }

                resMsg.set(39, "00");

                CountDownLatch doneSignal = new CountDownLatch(1);
                LoadResponseListener listener = new LoadResponseListener(doneSignal, 1);
                mux = QMUX.getMUX("load-mux");
                if (!processCode.substring(0, 2).equalsIgnoreCase("33")) {
                    Thread.sleep(respTime);
                }
                //mux.request(resMsg, 1000,listener, new Object());
                Space space = SpaceFactory.getSpace();
                space.out("load-send", resMsg);
                
            	System.out.println("******************************************************");
            	throw new InvalidOperationException("Transaction not handeled");
            }


        } catch (Exception e) {
            System.out.println("Error in process: Class " + e.toString());
        }
        return true;
    }
}
