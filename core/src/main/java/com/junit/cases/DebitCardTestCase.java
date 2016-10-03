/**
 * 
 */
package com.junit.cases;


import java.io.File;

import loadtest.LoadResponseListener;
import loadtest.TestCards;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.MUX;
import org.jpos.q2.Q2;
import org.jpos.q2.iso.QMUX;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.util.NameRegistrar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.en.loaddata.CardDetails;
import com.en.loaddata.LoadData;
import com.junit.util.DBHandler;

import static org.junit.Assert.*;

/**
 * @author ATM-Switch
 *
 */
public class DebitCardTestCase {

	private static LoadData ld;
	private static CardDetails[] cd;
	private static MUX mux = null;
	private static TestCards tc = new TestCards();
	public static Q2 q2;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] str = new String[0];
		DBHandler dbh = new DBHandler();
		q2 = new Q2();
	    //q2.main(str);
		q2.start();
	    Thread.sleep(10000);
	    ld = new LoadData();
	    ld.loadXml(new File("./cfg/LoadData_Debit.xml"));
	    cd = ld.getCardDetails();
	    mux=QMUX.getMUX("load-mux");
	    Object common = new Object();
	    NameRegistrar.register("COMMON", common);
	    dbh.setAccountBalances();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		q2.stop();
		Thread.sleep(10000);
	}

	@Test
	public void IssuerBalanceInquiryTest() {
		processRequest("00", 0);	
	}
	
	@Test
	public void IssuerCashWithdrawalTest() {
		processRequest("00", 1);
	}
	
	@Test
	public void IssuerCashPurchaseTest() {
		processRequest("00", 2);
	}
		
	@Test
	public void IssuerBillPaymentTest() {
		processRequest("00", 3);
	}
	
	@Test
	public void CustomerInquiryMEBtoMEB() {
		processRequest("00", 4);
	}
	
	@Test
	public void FundTransferMEBtoMEB() {
		processRequest("00", 5);
	}

	@Test
	public void CustomerInquiryOthertoMEB() {
		processRequest("00", 6);
	}

	@Test
	public void FundTransferOthertoMEB() {
		processRequest("00", 7);
	}

	@Test
	public void FundTransferMEBtoOther() {
		processRequest("00", 8);
	}

	@Test
	public void CashWithdrawReversal() {
		processRequestReversal("00", 1, 9);
	}
	
	@Test
	public void PurchaseReversal() {
		processRequestReversal("00", 2, 10);
	}
	
	@Test
	public void BillPaymentReversal() {
		processRequestReversal("00", 3, 11);
	}
	
	@Test
	public void FundTransferMEBtoMEBReversal() {
		processRequestReversal("00", 5, 12);
	}
	
	@Test
	public void FundTransferOtherToMEBReversal() {
		processRequestReversal("00", 7, 13);
	}
	
	@Test
	public void FundTransferMEBToOtherReversal() {
		processRequestReversal("00", 8, 14);
	}
	
	void processRequest(String expectedResponse, int txnNumber) {
		try {
			LoadResponseListener listener=new LoadResponseListener(null,1);
	    	Space space_ = SpaceFactory.getSpace("STAN");
	    	long stan = SpaceUtil.nextLong(space_, "STAN");
	    	ISOMsg m=tc.getTestMsg(stan, cd[txnNumber]);
	    	mux=QMUX.getMUX("load-mux");
	    	if (mux != null && mux.isConnected()) {
	    		mux.request(m, 1000,listener, new Object());
	    	} else {
	    		System.out.println("\nSwitch is not connected");
	    		assertTrue(false);
	    	}
	    	Object common = NameRegistrar.getIfExists("COMMON");
	    	synchronized(common) {
	    		common.wait(50000);
	    	}
	    	ISOMsg response = (ISOMsg)NameRegistrar.getIfExists("RESPONSE");
	    	if(response != null) {
	    		assertEquals(expectedResponse, response.getString("39"));
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	void processRequestReversal(String expectedResponse, int txnNumber, int revNumber) {
		try {
			LoadResponseListener listener=new LoadResponseListener(null,1);
	    	ISOMsg m=tc.getTestMsg(0, cd[txnNumber]);
	    	mux=QMUX.getMUX("load-mux");
	    	if (mux != null && mux.isConnected()) {
	    		mux.request(m, 1000,listener, new Object());
	    	} else {
	    		System.out.println("\nSwitch is not connected");
	    		assertTrue(false);
	    	}
	    	Object common = NameRegistrar.getIfExists("COMMON");
	    	synchronized(common) {
	    		common.wait(50000);
	    	}
	    	ISOMsg response = (ISOMsg)NameRegistrar.getIfExists("RESPONSE");
	    	if(response != null) {
	    		//assertEquals(expectedResponse, response.getString("39"));
	    		String resVal = response.getString("39");
	    		if(resVal.equalsIgnoreCase("00")) {
	    	    	m=tc.getTestMsg(0, cd[revNumber]);
	    	    	if (mux != null && mux.isConnected()) {
	    	    		mux.request(m, 1000,listener, new Object());
	    	    	} else {
	    	    		System.out.println("\n Switch is not connected");
	    	    		assertTrue(false);
	    	    	}
	    	    	synchronized(common) {
	    	    		common.wait(50000);
	    	    	}
	    	    	ISOMsg revRes = (ISOMsg)NameRegistrar.getIfExists("RESPONSE");
	    	    	if(response != null) {
	    	    		assertEquals(expectedResponse, revRes.getString("39"));
	    	    	} else {
	    	    		assertTrue(false);
	    	    	}
	    		} else {
	    			assertTrue("Main Transaction no successful", false);
	    		}
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
