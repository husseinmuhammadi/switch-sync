package com.junit.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.en.cardutil.EncryptDecryptCardNumber;

public class DBHandler {

	private Connection getDMConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" +
					PropertyLoader.getInstance().getProperty("DB_DM_IP") + ":" + 
					PropertyLoader.getInstance().getProperty("DB_DM_PORT") + ":" +
					PropertyLoader.getInstance().getProperty("DB_DM_DATABASE"), 
					PropertyLoader.getInstance().getProperty("DB_DM_USERNAME"), 
					PropertyLoader.getInstance().getProperty("DB_DM_PASSWORD"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return connection;
	}
	
	private Connection getSwitchConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" +
					PropertyLoader.getInstance().getProperty("DB_SWITCH_IP") + ":" + 
					PropertyLoader.getInstance().getProperty("DB_SWITCH_PORT") + ":" +
					PropertyLoader.getInstance().getProperty("DB_SWITCH_DATABASE"), 
					PropertyLoader.getInstance().getProperty("DB_SWITCH_USERNAME"), 
					PropertyLoader.getInstance().getProperty("DB_SWITCH_PASSWORD"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return connection;
	}
	
	public void deleteDebitCardBillPay() {
		deleteBillPay(PropertyLoader.getInstance().getProperty("BILLID_DEBIT_CARD"), 
				PropertyLoader.getInstance().getProperty("PAYID_DEBIT_CARD"), 
				PropertyLoader.getInstance().getProperty("COMPANYID_DEBIT_CARD"));
	}
	
	public void deleteGiftCardBillPay() {
		deleteBillPay(
				PropertyLoader.getInstance().getProperty("BILLID_GIFT_CARD"),
				PropertyLoader.getInstance().getProperty("PAYID_GIFT_CARD"), 
				PropertyLoader.getInstance().getProperty("COMPANYID_GIFT_CARD"));
	}
	
	public void deleteBonCardBillPay() {
		deleteBillPay(
				PropertyLoader.getInstance().getProperty("BILLID_BON_CARD"),
				PropertyLoader.getInstance().getProperty("PAYID_BON_CARD"), 
				PropertyLoader.getInstance().getProperty("COMPANYID_BON_CARD"));
	}
	
	public void goOffline() {
		updateGoOffline("Y");
	}

	public void goOnline() {
		updateGoOffline("N");
	}

	public void setAccountBalances() {
		BigDecimal balance = new BigDecimal("50000000");
		BigDecimal accntISN1 = new BigDecimal("910010000003858");
		BigDecimal accntISN2 = new BigDecimal("910020000004994");
		
		updateAccountBalance(accntISN1, balance);
		updateAccountBalance(accntISN2, balance);
	}
	
	public void updateGiftCardBalance() {
		BigDecimal balance = new BigDecimal("20000000");
		updateGIFTCardBalance(PropertyLoader.getInstance().getProperty("GIFT_CARD_NUMBER"), balance);
		updateGIFTCardBalance(PropertyLoader.getInstance().getProperty("GIFT_CARD_NUMBER2"), balance);
	}
	
	public void updateBonCardBalance() {
		BigDecimal balance = new BigDecimal("20000000");
		updateGIFTCardBalance(PropertyLoader.getInstance().getProperty("BON_CARD_NUMBER"), balance);
	}
	
	private void deleteBillPay(String billId, String payId, String companyId) {
		Connection con = getDMConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM BILLPAY WHERE TO_NUMBER (BILLID) = ? AND TO_NUMBER (PAYID) =?  AND COMPAYID =?");
			ps.setString(1, billId);
			ps.setString(2, payId);
			ps.setString(3, companyId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	private void updateGoOffline(String status) {
		Connection con = getDMConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE ATMSTATUS SET GO_OFFLINE = ?");
			ps.setString(1, status);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void updateAccountBalance(BigDecimal accntIsn, BigDecimal balance) {
		Connection con = getDMConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE ACCOUNTMASTER SET LOCALLEDGERBALANCE = ?, LKLAVAILBAL = ?" +
					" WHERE ACCOUNTISN = ?");
			ps.setBigDecimal(1, balance);
			ps.setBigDecimal(2, balance);
			ps.setBigDecimal(3, accntIsn);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void updateGIFTCardBalance(String cardNumber, BigDecimal balance) {
		Connection con = getSwitchConnection();
		PreparedStatement ps = null;
		String encryptCard = EncryptDecryptCardNumber.getEncryptedCardNumber(cardNumber);
		try {
			ps = con.prepareStatement("UPDATE GIFT_CARD_ACCOUNTMASTER SET AVAILABLE_BALANCE = ?" +
					" WHERE CARDNUMBER = ?");
			ps.setBigDecimal(1, balance);
			ps.setString(2, encryptCard);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
