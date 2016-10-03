package com.en.pinhsm;

 
import org.jpos.iso.ISOUtil;
import org.jpos.security.EncryptedPIN;

public class PINService
{
	private String zmk;
	private String key1;
	private String key2;
	private String key3;
    public PINService(String zmk,String key1,String key2,String key3){
	     this.zmk=zmk;
    	 this.key1=key1;
	     this.key2=key2;
	     this.key3=key3;
    }

    public String createPinBlock(String span, String pin)
        throws Exception
    {
    	byte pb[];
        String pan = EncryptedPIN.extractAccountNumberPart(span);
        if(key1.length() == 32)
        {
            byte part1[] = ISOUtil.hex2byte(key1);
            byte part2[] = ISOUtil.hex2byte(key2);
            byte part3[] = ISOUtil.hex2byte(key3);
            byte key[] = ArrayUtil.xor(part1, part2, part3);
            byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(zmk), key, CryptoUtil.DECRYPT_MODE);
            PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
            pb = ph.getEncryptedPin_DKE(pin, pan);
        } else
        {
            byte part1[] = ISOUtil.hex2byte(key1);
            byte part2[] = ISOUtil.hex2byte(key2);
            byte part3[] = ISOUtil.hex2byte(key3);
            byte key[] = ArrayUtil.xor(part1, part2, part3);
            byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(zmk), key, CryptoUtil.DECRYPT_MODE);
            PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
            pb = ph.getEncryptedPin(pin, pan);
        }
        return ISOUtil.hexString(pb);
    }
    public boolean verifyPin(String pb, String accountNumber,String pin){
    	String rpin;
		try {
			rpin = decryptPinBlock(pb, accountNumber);
			return rpin.equalsIgnoreCase(pin);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	
    	
    }
    
    public String decryptPinBlock(String pb, String accountNumber)
        throws Exception
    {
        String rpin = null;
        if(key1.length() == 32)
        {
        	   byte part1[] = ISOUtil.hex2byte(key1);
               byte part2[] = ISOUtil.hex2byte(key2);
               byte part3[] = ISOUtil.hex2byte(key3);
               byte key[] = ArrayUtil.xor(part1, part2, part3);
            
            byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(zmk), key, CryptoUtil.DECRYPT_MODE);
            System.out.println("the pin block is "+ISOUtil.hex2byte(zmk));
            byte cpb[] = CryptoUtil.desede(ISOUtil.hex2byte(pb), zpk, CryptoUtil.DECRYPT_MODE);
            PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
            System.out.println("the ph is "+ph);
            rpin = ph.calculatePIN(cpb, (byte)1, EncryptedPIN.extractAccountNumberPart(accountNumber));
            System.out.println("rpin is "+rpin);
        } else
        {
        	   byte part1[] = ISOUtil.hex2byte(key1);
               byte part2[] = ISOUtil.hex2byte(key2);
               byte part3[] = ISOUtil.hex2byte(key3);
               byte key[] = ArrayUtil.xor(part1, part2, part3);
              byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(zmk), key, CryptoUtil.DECRYPT_MODE);
            byte cpb[] = CryptoUtil.des(ISOUtil.hex2byte(pb), zpk, CryptoUtil.DECRYPT_MODE);
            PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
            rpin = ph.calculatePIN(cpb, (byte)1, EncryptedPIN.extractAccountNumberPart(accountNumber));
        }
        return rpin;
    }

    
}