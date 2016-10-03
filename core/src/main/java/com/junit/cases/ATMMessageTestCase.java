package com.junit.cases;

import java.io.FileInputStream;

import org.jpos.core.SimpleConfiguration;
import org.jpos.iso.packager.DummyPackager;
import org.jpos.q2.Q2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.en.listener.AtmChannel;
import com.en.listener.AtmNdc;
import com.en.listener.AtmPackager;
import com.junit.util.DBHandler;

import static org.junit.Assert.*;

public class ATMMessageTestCase {

	private static Q2 q2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		q2 = new Q2();
		q2.start();
	    Thread.sleep(10000);
		System.out.println("Deleting BillPays ... ");
		DBHandler dbh = new DBHandler();
		dbh.deleteBonCardBillPay();
		dbh.deleteDebitCardBillPay();
		dbh.deleteGiftCardBillPay();
		dbh.updateBonCardBalance();
	    dbh.setAccountBalances();
	    dbh.updateGiftCardBalance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		q2.stop();
		Thread.sleep(10000);
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void OnUsBalanceInquiryDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/1. BALANCE INQUIRY.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "359");
	}
	
	@Test
	public void OnUsWithdrawDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/2. CASH_WITHDRAWAL.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "361");
	}
	
	@Test
	public void OnUsValidateBillPaymentDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/9. VALIDATE_BILL_PAYMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "301");
	}
	
	@Test
	public void OnUsBillPaymentDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/3. BILL_PAYMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "308");
	}
	
	@Test
	public void OnUsCustInquiryMEBtoMEBDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/4. CUST_INQUIRY_MEB_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "286");
	}
	
	@Test
	public void OnUsFundTransMEBtoMEBDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/5. FUND_TRANSFER_MEB_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "332");
	}
	
	@Test
	public void OnUsCustInquiryMEBtoOtherDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/6. CUST_INQUIRY_MEB_TO_OTHER.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "286");
	}
	
	@Test
	public void OnUsFundTransMEBtoOtherDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/7. FUND_TRANSFER_MEB_TO_OTHER.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "332");
	}
	
	@Test
	public void OnUsMinistatementDebitCard() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ON_US/8. MINISTATEMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "299");
	}

	@Test
	public void AcquirerBalanceInquiry() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ACQUIRER/1. BALANCE INQUIRY.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "359");
	}
	
	@Test
	public void AcquirerWithdrawal() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ACQUIRER/2. CASH_WITHDRAWAL.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "361");
	}

	@Test
	public void AcquirerCustInquiryOtherToMEB() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ACQUIRER/3. CUST_INQUIRY_OTHER_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "290");
	}

	@Test
	public void AcquirerFundTransferOtherToMEB() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ACQUIRER/4. FUND_TRANSFER_OTHER_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "332");
	}
	
	@Test
	public void AcquirerCustInquiryOtherToOther() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ACQUIRER/5. CUST_INQUIRY_OTHER_TO_OTHER.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "290");
	}
	
	@Test
	public void AcquirerFundTransferOtherToOther() throws Exception {
		String fileName = "./atmmessages/DEBIT_CARD/ACQUIRER/6. FUND_TRANSFER_OTHER_TO_OTHER.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "332");
	}
	
	@Test
	public void GiftCardBalanceInquiry() throws Exception {
		String fileName = "./atmmessages/GIFTCARD/BALANCE_INQUIRY_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "359");
	}
	
	@Test
	public void GiftCardWithdrawal() throws Exception {
		String fileName = "./atmmessages/GIFTCARD/CASH_WITHDRAW_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "361");
	}
	
	@Test
	public void GiftCardMinistatement() throws Exception {
		String fileName = "./atmmessages/GIFTCARD/MINISTATEMENT_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "299");
	}
	
	@Test
	public void GiftCardValidateBillPayment() throws Exception {
		String fileName = "./atmmessages/GIFTCARD/VALIDATE_BILL_PAYMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "301");
	}
	
	@Test
	public void GiftCardBillPayment() throws Exception {
		String fileName = "./atmmessages/GIFTCARD/BILL_PAYMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "308");
	}
	
	@Test
	public void OnUsBalanceInquiryBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/BALANCE_INQUIRY_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "359");
	}
	
	@Test
	public void OnUsWithdrawBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/CASH_WITHDRAW_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "361");
	}
	
	@Test
	public void OnUsValidateBillPaymentBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/VALIDATE_BILL_PAYMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "301");
	}
	
	@Test
	public void OnUsBillPaymentBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/BILL_PAYMENT.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "308");
	}
	
	@Test
	public void OnUsCustInquiryDebit2BONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/CUST_ENQ_MEB_TO_MEB_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "286");
	}
	
	@Test
	public void OnUsFundTransferDebit2BONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/FUND_TRANSFER_MEB_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "332");
	}
	
	@Test
	public void OnUsCustInquiryBONCardtoOtherBankCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/CUST_ENQ_MEB_TO_OTHER_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "286");
	}
	
	@Test
	public void OnUsFundTransBONCardtoOtherBankCard() throws Exception {
		//String fileName = "./atmmessages/BON_CARD/FUND_TRANSFER_MEB_TO_OTHER.txt";
		//String nextState = sendNdcRequest1(fileName);
		//assertEquals(nextState, "338");
	}
	
	@Test
	public void OnUsCustInquiryBONtoBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/CUST_ENQ_MEB(BON)_TO_MEB(BON)_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "286");
	}
	
	@Test
	public void OnUsFundTransBONtoBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/FUND_TRANSFER_MEB(BON)_TO_MEB(BON).txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "338");
	}
	
	@Test
	public void OnUsCustInquiryOthertoBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/CUST_ENQ_OTHER_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "290");
	}
	
	@Test
	public void OnUsFundTransOtherToBON() throws Exception {
		String fileName = "./atmmessages/BON_CARD/FUND_TRANSFER_OTHER_TO_MEB.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "332");
	}
	
	@Test
	public void OnUsMinistatementBONCard() throws Exception {
		String fileName = "./atmmessages/BON_CARD/MINISTATEMENT_EN.txt";
		String nextState = sendNdcRequest1(fileName);
		assertEquals(nextState, "299");
	}
	
	public String sendNdcRequest1(String fileName) {
			String nextState = "";
		try {
			
			FileInputStream fileInputstream = new FileInputStream(fileName);
			StringBuffer stringbuffer = new StringBuffer();
			int i;
			while ((i = fileInputstream.read()) != -1)
				stringbuffer.append((char) i);
			
			AtmPackager atmPack = new AtmPackager("file:./cfg/atm/ndc-");
			AtmNdc ndcMsgReq= new AtmNdc(atmPack);
			ndcMsgReq.unpack(stringbuffer.toString().getBytes());
			AtmChannel atmChannel = new AtmChannel("localhost",9876, new DummyPackager());
			SimpleConfiguration simconf = new SimpleConfiguration();
			simconf.put("schema", "file:./cfg/atm/ndc-");
			atmChannel.setConfiguration(simconf);
			atmChannel.setTimeout(40000);
			atmChannel.connect();
			atmChannel.send(stringbuffer.toString().getBytes());
			AtmNdc ndcMsgRes = (AtmNdc)atmChannel.receive();
			
			AtmPackager atmpackRes = ndcMsgRes.getFSDMsg();
			
			System.out.println("Next State Number is = " + atmpackRes.get("next-state-id", "000"));
			
			nextState = atmpackRes.get("next-state-id", "000");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return nextState;
			
		}
		return nextState;
	}
}
