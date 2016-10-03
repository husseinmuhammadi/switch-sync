package com.en.pinhsm;

import org.jpos.iso.ISOUtil;
import org.jpos.security.EncryptedPIN;

import com.en.pinhsm.CryptoUtil;
import com.en.pinhsm.PinHandler;

public class PinUtil {

	public String createPinBlock(String span, String pin) throws Exception {
		String pan = EncryptedPIN.extractAccountNumberPart(span);
		if (ISO8583TesterUtil.getInstance().getCC1().length() == 32) {
			byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
			byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
			byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			byte[] pb = ph.getEncryptedPin_DKE(pin, pan);
			return ISOUtil.hexString(pb);
		} else {
			byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
			byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
			byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			byte[] pb = ph.getEncryptedPin(pin, pan);
			return ISOUtil.hexString(pb);
		}

	}

	public String decryptPinBlock(String pb, String accountNumber) throws Exception {
		String rpin = null;
		if (ISO8583TesterUtil.getInstance().getCC1().length() == 32) {
			byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
			byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
			byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
			byte cpb[] = CryptoUtil.desede(ISOUtil.hex2byte(pb), zpk, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			rpin = ph.calculatePIN(cpb, (byte) 1, EncryptedPIN.extractAccountNumberPart(accountNumber));
			System.out.println((new StringBuilder("The pin")).append(rpin).toString());
		} else {
			byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC1());
			byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC2());
			byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getCC3());
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getZPK()), key, CryptoUtil.DECRYPT_MODE);
			byte cpb[] = CryptoUtil.des(ISOUtil.hex2byte(pb), zpk, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			rpin = ph.calculatePIN(cpb, (byte) 1, EncryptedPIN.extractAccountNumberPart(accountNumber));
			System.out.println((new StringBuilder("The pin")).append(rpin).toString());
		}
		return rpin;
	}

	public String createAtmPinBlock(String span, String pin) throws Exception {
		String pan = EncryptedPIN.extractAccountNumberPart(span);
		if (ISO8583TesterUtil.getInstance().getTMK1().length() == 32) {
			byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getTMK1());
			byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getTMK2());
			byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getTMK3());
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getxTPK()), key, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			byte[] pb = ph.getEncryptedPin_DKE(pin, pan);
			return hextoGraphics(ISOUtil.hexString(pb));
		} else {
			byte part1[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getTMK1());
			byte part2[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getTMK2());
			byte part3[] = ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getTMK3());
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.des(ISOUtil.hex2byte(ISO8583TesterUtil.getInstance().getxTPK()), key, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			byte[] pb = ph.getEncryptedPin(pin, pan);
			return hextoGraphics(ISOUtil.hexString(pb));
		}

	}

	public static String hex2graph(String s) {
		String graphicDigits = "0123456789:;<=>?";
		String hexDigits = "0123456789ABCDEF";

		s = s.toUpperCase();
		String hexString = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = hexDigits.indexOf(c);
			char val = graphicDigits.charAt(d);
			hexString = hexString + val;
		}
		return hexString;
	}

	public static String graphic2hex(String s) {
		String graphicDigits = "0123456789:;<=>?";
		String hexDigits = "0123456789ABCDEF";
		s = s.toUpperCase();
		String hexString = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = graphicDigits.indexOf(c);
			char val = hexDigits.charAt(d);
			hexString = hexString + val;
		}
		return hexString;
	}

	public static String hextoGraphics(String s) {
		String graphicDigits = "0123456789:;<=>?";
		String hexDigits = "0123456789ABCDEF";
		s = s.toUpperCase();
		String graphicString = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = hexDigits.indexOf(c);
			char val = graphicDigits.charAt(d);
			graphicString = graphicString + val;
		}
		return graphicString;
	}

	public static String getTrack2DataWithoutSentimental(String track2Data) {
		String newTrack2Data = "";
		boolean isStartsentimentalNumber = isNumeric(track2Data.substring(0, 1));
		boolean isEndsentimentalNumber = false;
		if (isStartsentimentalNumber) {
			newTrack2Data = track2Data;
		} else {
			newTrack2Data = track2Data.substring(1);
		}

		isEndsentimentalNumber = isNumeric(newTrack2Data.substring(newTrack2Data.length() - 1));
		if (!isEndsentimentalNumber) {
			newTrack2Data = newTrack2Data.substring(0, newTrack2Data.length() - 1);
		}

		return newTrack2Data;

	}

	public static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static String createATMPinBlock(String span, String pin) throws Exception {
		String pan = EncryptedPIN.extractAccountNumberPart(span);
			byte part1[] = ISOUtil.hex2byte("29C1FE022531E31964D32607DF754F10");
			byte part2[] = ISOUtil.hex2byte("2FBF9264FDC8D376A46DE6CDFE190B08");
			byte part3[] = ISOUtil.hex2byte("00000000000000000000000000000000");
			byte key[] = ArrayUtil.xor(part1, part2, part3);
			byte zpk[] = CryptoUtil.desede(ISOUtil.hex2byte("E75D98FCED71615D0AD2D25490D84E81"), key, CryptoUtil.DECRYPT_MODE);
			PinHandler ph = new PinHandler(ISOUtil.hexString(zpk));
			byte[] pb = ph.getEncryptedPin_DKE(pin, pan);
			return hextoGraphics(ISOUtil.hexString(pb));
	}
	public static void main(String args[]) throws Exception {
		String pan = "6276481011111115";
		String pin = "1234";
		System.out.println("ATM PB = " + createATMPinBlock(pan, pin));
	}
}
