package com.en.cardutil;


public class EncryptDecryptCardNumber {

	public static void main(String args[]) {

	}
	
	
	public static String getEncryptedCardNumber(String custCardNumber) {
		try {
			String encryptedCardNumber = TripleDESEncryptionDecryption.des3ECB(CardConsatant.KEY.getBytes(), custCardNumber.getBytes(), 1);
			return encryptedCardNumber;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDecryptedCardNumber(String custCardNumber) {
		try {
			String encryptedCardNumber = TripleDESEncryptionDecryption.des3ECB(CardConsatant.KEY.getBytes(), custCardNumber.getBytes(), 2);
			return encryptedCardNumber;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
