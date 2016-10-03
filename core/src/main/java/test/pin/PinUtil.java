package test.pin;

import org.jpos.iso.ISOUtil;
import org.jpos.security.EncryptedPIN;

import test.pin.CryptoUtil;
import test.pin.PinHandler;

 

public class PinUtil {
    public String createPinBlock(String span, String pin)
            throws Exception
        {
            String pan = EncryptedPIN.extractAccountNumberPart(span);
            if(ISO8583TesterUtil.getInstance().getCC1().length() == 32)
            {
                byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
                byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
                byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
                byte key[] = ArrayUtil.xor(part1, part2, part3);
                byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
                PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
                byte[] pb = ph.getEncryptedPin_DKE(pin, pan);
                return ISOUtil.hexString(pb);
            } else
            {
                byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
                byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
                byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
                byte key[] = ArrayUtil.xor(part1, part2, part3);
                byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
                PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
                byte[]  pb = ph.getEncryptedPin(pin, pan);
                return ISOUtil.hexString(pb);
            }
            
        }

        public String decryptPinBlock(String pb, String accountNumber)
            throws Exception
        {
            String rpin = null;
            if(ISO8583TesterUtil.getInstance().getCC1().length() == 32)
            {
                byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
                byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
                byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
                byte key[] = ArrayUtil.xor(part1, part2, part3);
                byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
                byte cpb[] = CryptoUtil.desede(ISOUtil.hex2byte(pb), zpk, CryptoUtil.DECRYPT_MODE);
                PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
                rpin = ph.calculatePIN(cpb, (byte)1, EncryptedPIN.extractAccountNumberPart(accountNumber));
                System.out.println( "The PIN = " + rpin);
            } else
            {
                byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
                byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
                byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
                byte key[] = ArrayUtil.xor(part1, part2, part3);
                byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
                byte cpb[] = CryptoUtil.des(ISOUtil.hex2byte(pb), zpk, CryptoUtil.DECRYPT_MODE);
                PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
                rpin = ph.calculatePIN(cpb, (byte)1, EncryptedPIN.extractAccountNumberPart(accountNumber));
                System.out.println( "The PIN = " + rpin);
            }
            return rpin;
        }
        public static void main(String args[]) {
        	try {
        		String dpinblck = new PinUtil().decryptPinBlock("2E08DEE23E1805C0", "456110000720");
        		System.out.println("Pin block : " + dpinblck);
        	} catch (Exception e) {
        		System.out.println(e.toString());
        	}
        }
}
