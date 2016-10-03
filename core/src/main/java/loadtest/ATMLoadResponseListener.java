package loadtest;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOResponseListener;

import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;

public class ATMLoadResponseListener implements ISOResponseListener {
    int exp=0;
    int res=0;
    CountDownLatch doneSignal = null;
    static Logger logger = Logger.getLogger(ATMLoadResponseListener.class);
    static int totalSuccess = 0;
    static int totalfailure = 0;
    static int totalExpired = 0;
    
    public ATMLoadResponseListener( CountDownLatch doneSignal,int count){
    	 this.doneSignal =doneSignal;
    }
	 
	public void expired(Object arg0) {
		totalExpired = totalExpired + 1;
		doneSignal.countDown();
	}
	
	public void responseReceived(ISOMsg arg0, Object arg1) {
		AtmNdc ndcMsgRes = (AtmNdc)arg0;
		AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();
		if(atmpackRes.get("next-state-id", "000").equalsIgnoreCase("361")
				|| atmpackRes.get("next-state-id", "000").equalsIgnoreCase("299")
				|| atmpackRes.get("next-state-id", "000").equalsIgnoreCase("359")
				|| atmpackRes.get("next-state-id", "000").equalsIgnoreCase("332")) {
			totalSuccess = totalSuccess + 1;
		} else {
			System.out.println("Failure : "  + atmpackRes.get("next-state-id", "000"));
			totalfailure = totalfailure + 1;
		}
		doneSignal.countDown();
	}
	
	public static int getTotalSuccess() {
		return totalSuccess;
	}
	
	public static void setTotalSuccess(int totalSuc) {
		totalSuccess = totalSuc;
	}
	
	public static int getTotalFailure() {
		return totalfailure;
	}
	
	public static void setTotalFailure(int totalFail) {
		totalfailure = totalFail;
	}
	
	public static int getTotalExpired() {
		return totalExpired;
	}
	
	public static void setTotalExpired(int totalEx) {
		totalExpired = totalEx;
	}
}
