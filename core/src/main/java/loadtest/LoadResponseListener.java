package loadtest;


import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOResponseListener;

public class LoadResponseListener implements ISOResponseListener {
    int exp=0;
    int res=0;
    CountDownLatch doneSignal = null;
    static Logger logger = Logger.getLogger(LoadResponseListener.class);
    static int totalSuccess = 0;
    static int totalfailure = 0;
    public LoadResponseListener( CountDownLatch doneSignal,int count){
    	 this.doneSignal =doneSignal;
    }
	 
	public void expired(Object arg0) {
		//logger.info("Reponse Recieved In Expired: " + (new Date()).toString());
		//doneSignal.countDown();
	}
	
	public void responseReceived(ISOMsg arg0, Object arg1) {
		logger.info("Reponse Recieved :  " + arg0.getString(11) +  " : Response : " + arg0.getString(39) + (new Date()).toString());
		if(arg0.getString(39) != null && arg0.getString(39).equalsIgnoreCase("00")) {
			totalSuccess = totalSuccess + 1;
		} else {
			totalfailure = totalfailure + 1;
		}
		doneSignal.countDown();
	}
	
	public static int getTotalSuccess() {
		return totalSuccess;
	}
	
	public static int getTotalFailure() {
		return totalfailure;
	}
}
