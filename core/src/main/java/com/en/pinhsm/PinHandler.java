package com.en.pinhsm;

import javax.crypto.Cipher;

import org.jpos.iso.ISOUtil;

import test.pin.CryptoUtil;

public class PinHandler {
	public static final byte FORMAT00=0x00;
	public static final byte FORMAT01=0x01;
	public static final byte FORMAT02=0x02;
	public static final byte FORMAT03=0x03;
	public static final byte FORMAT04=0x04;
	public static final byte FORMAT05=0x04;
	public static final byte FORMAT06=0x04;
	public static final int MAX_PIN_LENGTH=6;
	byte[] translatedPINBlock=null;
	private String awk;
	public PinHandler(String key){
		this.awk=key;
	}
	public byte[] getEncryptedPin(String pin,String accountNumber) throws Exception{
		
		 byte[] clearPINBlock = calculatePINBlock(pin, FORMAT01, accountNumber);
		 byte[] bKey=ISOUtil.hex2byte(awk);
		 //System.out.println("CLEARPB:"+ISOUtil.hexString(clearPINBlock));
		 byte[] translatedPINBlock = CryptoUtil.des(clearPINBlock,bKey,Cipher.ENCRYPT_MODE);
		 return translatedPINBlock;
	}
	
	public byte[] getEncryptedPin_DKE(String pin,String accountNumber) throws Exception{
		try{
		 byte[] clearPINBlock = calculatePINBlock(pin, FORMAT01, accountNumber);
		 byte[] bKey=ISOUtil.hex2byte(awk);
		 translatedPINBlock = CryptoUtil.desede(clearPINBlock,bKey,Cipher.ENCRYPT_MODE);
		}catch(Exception e){
			e.printStackTrace();
		}
		 return translatedPINBlock;
		 
		
	}
	    /**
	     * Calculates the clear PIN Block
	     * @param pin as entered by the card holder on the PIN entry device
	     * @param pinBlockFormat
	     * @param accountNumber (the 12 right-most digits of the account number excluding the check digit)
	     * @return The clear PIN Block
	     * @throws SMException
	     *
	     */
	    private byte[] calculatePINBlock (String pin, byte pinBlockFormat, String accountNumber) throws PINException {
	        byte[] pinBlock = null;
	        if (pin.length() > MAX_PIN_LENGTH)
	            throw  new PINException("Invalid PIN length: " + pin.length());
	        if (accountNumber.length() != 12)
	            throw  new PINException("Invalid Account Number: " + accountNumber + ". The length of the account number must be 12 (the 12 right-most digits of the account number excluding the check digit)");
	        switch (pinBlockFormat) {
	            case FORMAT00: // same as FORMAT01
	            case FORMAT01:
	                {
	                    // Block 1
	                    String block1 = null;
	                    byte[] block1ByteArray;
	                    switch (pin.length()) {
	                        // pin length then pad with 'F'
	                        case 4:
	                            block1 = "04" + pin + "FFFFFFFFFF";
	                            break;
	                        case 5:
	                            block1 = "05" + pin + "FFFFFFFFF";
	                            break;
	                        case 6:
	                            block1 = "06" + pin + "FFFFFFFF";
	                            break;
	                        default:
	                            throw  new PINException("Unsupported PIN Length: " +
	                                    pin.length());
	                    }
	                    block1ByteArray = ISOUtil.hex2byte(block1);
	                    // Block 2
	                    String block2;
	                    byte[] block2ByteArray = null;
	                    block2 = "0000" + accountNumber;
	                    block2ByteArray = ISOUtil.hex2byte(block2);
	                    // pinBlock
	                    pinBlock = ISOUtil.xor(block1ByteArray, block2ByteArray);
	                }
	                ;
	                break;
	            case FORMAT03: 
	                {
	                    if(pin.length() < 4 || pin.length() > 12) 
	                        throw new PINException("Unsupported PIN Length: " + 
	                                pin.length());
	                    pinBlock = ISOUtil.hex2byte (
	                        pin + "FFFFFFFFFFFFFFFF".substring(pin.length(),16)
	                    );
	                }
	                break;
	            default:
	                throw  new PINException("Unsupported PIN format: " + pinBlockFormat);
	        }
	        return  pinBlock;
	    }
	    
	    public static void main(String args[]) {
	    	try {
	    		byte[] clearPINBlock = new PinHandler("").calculatePINBlock ("2834", (byte)0x01, "456110000720");
	    		System.out.println("Pin block : " + ISOUtil.hexString(clearPINBlock));
	    		byte[] lmk = ISOUtil.hex2byte("01020304050607080102030405060708");
	    		byte[] ezpk = ISOUtil.hex2byte("8E3985FD8754DECBBE9C62B518BC3114");
	    		byte[] zpk = CryptoUtil.desede(ezpk, lmk, CryptoUtil.DECRYPT_MODE);
	    		byte[] encPinBlock = CryptoUtil.desede(clearPINBlock, zpk, CryptoUtil.ENCRYPT_MODE);
	    		System.out.println("Pin block : " + ISOUtil.hexString(encPinBlock));
	    	}catch(Exception e){
	    		
	    	}
	    }
	    
	    /**
	     * Calculates the clear pin (as entered by card holder on the pin entry device)
	     * givin the clear PIN block
	     * @param pinBlock clear PIN Block
	     * @param pinBlockFormat
	     * @param accountNumber
	     * @return the pin
	     * @throws SMException
	     */
	    public  String calculatePIN (byte[] pinBlock, byte pinBlockFormat, String accountNumber) throws CryptoException {
	        String pin = null;
	        int pinLength;
	        if (accountNumber.length() != 12)
	            throw  new CryptoException("Invalid Account Number: " + accountNumber + ". The length of the account number must be 12 (the 12 right-most digits of the account number excluding the check digit)");
	        switch (pinBlockFormat) {
	            case FORMAT00: // same as format 01
	            case FORMAT01:
	                {
	                    // Block 2
	                    String block2;
	                    block2 = "0000" + accountNumber;
	                    byte[] block2ByteArray = ISOUtil.hex2byte(block2);
	                    // get Block1
	                    byte[] block1ByteArray = ISOUtil.xor(pinBlock, block2ByteArray);
	                    pinLength = Math.abs (block1ByteArray[0]);
	                    System.out.println("PL:"+pinLength);
	                    if (pinLength > MAX_PIN_LENGTH)
	                        throw  new CryptoException("PIN Block Error");
	                    // get pin
	                    String pinBlockHexString = ISOUtil.hexString(block1ByteArray);
	                    pin = pinBlockHexString.substring(2, pinLength
	                            + 2);
	                    String pad = pinBlockHexString.substring(pinLength + 2);
	                    pad = pad.toUpperCase();
	                    int i = pad.length();
	                    while (--i >= 0)
	                        if (pad.charAt(i) != 'F')
	                            throw new CryptoException("PIN Block Error");
	                }
	                break;
	            case FORMAT03: 
	                {
	                    String block1 = ISOUtil.hexString(pinBlock);
	                    int len = block1.indexOf('F');
	                    if(len == -1) len = 12;
	                    int i = block1.length();
	                    pin = block1.substring(0, len);

	                    while(--i >= len) 
	                        if(block1.charAt(i) != 'F') 
	                            throw new CryptoException("PIN Block Error");
	                    while(--i >= 0) 
	                        if(pin.charAt(i) >= 'A') 
	                            throw new CryptoException("PIN Block Error");

	                    if(pin.length() < 4 || pin.length() > 12) 
	                        throw new CryptoException("Unsupported PIN Length: " + 
	                                pin.length());
	                }
	                break;
	            default:
	                throw  new CryptoException("Unsupported PIN Block format: " + pinBlockFormat);
	        }
	        return  pin;
	    }
}
