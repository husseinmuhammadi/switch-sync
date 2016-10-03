package com.en.cardutil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.jpos.iso.ISOUtil;


public class TripleDESEncryptionDecryption {
	private static Cipher cipher = null;

	public static synchronized String des3ECB(byte key[], byte data[], int mode) throws Exception {
		try {
			if (cipher == null)
			{
				cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			}
			byte result[] = null;
			String encDecCardNumber = null;
			java.security.Key gkey = getSecretKey(key);
			cipher.init(mode, gkey);
			if(mode==1){
				//data = CardUtil.checkMultiplesOfEight(data);	// Check Card Number for Multiples of Eight			
				result = cipher.doFinal(data);
				System.out.println("Encrypted result : " + ISOUtil.hexString(result));
				encDecCardNumber = Base64Coder.encode(result);
				/*		System.out.println(encDecCardNumber); */
			}
			if(mode==2){
				encDecCardNumber = new String(data);
				data = Base64Coder.decode(encDecCardNumber);
				result = cipher.doFinal(data);
				encDecCardNumber = CardUtil.checkMode(result, mode);	//remove zero prefix				
				encDecCardNumber = new String(result);
		/*		System.out.println(encDecCardNumber); */
			}
			return encDecCardNumber;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public static SecretKey getSecretKey(byte keyData[]) throws Exception {
		java.security.spec.KeySpec keysp = null;
		SecretKey key = null;
		SecretKeyFactory keyf = null;
		//System.out.println("Length :" + keyData.length);
		String alg;
		if (keyData.length == 8) {
			keysp = new DESKeySpec(keyData);
			alg = "DES";
		} else if (keyData.length == 16) {
			byte xl[] = new byte[8];
			byte xr[] = new byte[8];
			split(keyData, xl, xr);
			byte mer[] = concat(xl, xr, xl);
			keysp = new DESedeKeySpec(mer);
			alg = "DESede";
		} else {
			// System.out.println((new StringBuilder()).append("Key length:").append(
			//    keyData.length).toString());
			keysp = new DESedeKeySpec(keyData);
			alg = "DESede";
		}
		keyf = SecretKeyFactory.getInstance(alg);
		key = keyf.generateSecret(keysp);
		return key;
	}

	public static final void split(byte abyte0[], byte left[], byte right[]) {
		System.arraycopy(abyte0, 0, left, 0, abyte0.length / 2);
		System.arraycopy(abyte0, abyte0.length / 2, right, 0, abyte0.length / 2);
	}

	public static final byte[] concat(byte abyte0[], byte abyte1[]) {
		byte ai[] = new byte[abyte0.length + abyte1.length];
		System.arraycopy(abyte0, 0, ai, 0, abyte0.length);
		System.arraycopy(abyte1, 0, ai, abyte0.length, abyte1.length);
		return ai;
	}

	public static final byte[] concat(byte abyte0[], byte abyte1[],
			byte abyte2[]) {
		byte ai[] = new byte[abyte0.length + abyte1.length];
		System.arraycopy(abyte0, 0, ai, 0, abyte0.length);
		System.arraycopy(abyte1, 0, ai, abyte0.length, abyte1.length);
		return concat(ai, abyte2);
	}

/*	public static void main(String[] args) throws Exception {		
		String cardNumber = "uXN2yrZGvdl/qOd0+C12g6u6XEojM/hD"; //"4454F8F6C30FF33A"; //"EDB1A4BEBABD04ABEACB3749E401540C";//"02372F7B481B0D07";//"4358120200003438";		
		System.out.println(TripleDESEncryptionDecryption.des3ECB(CardConsatant.KEY.getBytes(), cardNumber.getBytes(), 2));
	}*/

}
