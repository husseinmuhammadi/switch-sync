package loadtest;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: roohi
 * Date: 9/8/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mac1 {

    public byte[] key = new byte[]{0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C};
    //87A420BDAEBC3C47

    public static final Mac1 instance = new Mac1();

    public byte[] calcMac(ISOMsg msg) throws ISOException {
        return calcMac(msg, key);
    }

    public byte[] calcMac(ISOMsg msg, byte[] key) throws ISOException {
        byte[] input = extractBytes(msg);

        input = this.Normalize(input);
        byte[] buffer = new byte[8];
        try {
        	byte[] key1 = getFirstKey(key);
        	byte[] key2 = getSecondKey(key);
            for (int i = 0; (i * 8) < input.length; i++) {
                byte[] buffer2 = this.SubByte(input, i);
                byte[] inp = this.XOR(buffer, buffer2);
                buffer = this.Enc(inp, key1);
            }
            buffer = this.Enc(buffer, key2);
            buffer = this.Enc(buffer, key1);
        } catch (Exception x) {
            x.printStackTrace();
        }
        return buffer;
    }
    
    public byte[] getFirstKey(byte[] key) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            buffer[i] = key[i];
        }
        return buffer;
    }
    
    
    public byte[] getSecondKey(byte[] key) {
        byte[] buffer = new byte[8];
        buffer = this.SubByte(key, 8);
        return buffer;
    }

    private byte[] strToBytes(String s) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(i * 2, (i + 1) * 2), 16);
        }
        return bytes;
    }

    public byte[] pinEnc(String pin, String pan) {
    	key = ISOUtil.hex2byte("87A420BDAEBC3C47");
        return pinEnc(pin, pan, key);
    }

    public byte[] pinEnc(String pin, String pan, byte[] key) {
        try {
            String str1 = String.valueOf(pin.length());
            if (str1.length() < 2)
                str1 = "0" + str1;
            str1 = str1 + pin;
            while (str1.length() < 16)
                str1 = str1 + "F";
            String str2 = "0000" + pan.substring(3, 15);

            byte[] beforEnc = XOR(strToBytes(str1), strToBytes(str2));
            beforEnc = Normalize(beforEnc);
            return Enc(beforEnc, key);
        } catch (Exception x) {
            x.printStackTrace();

        }
        return new byte[8];
    }

    public byte[] Enc(byte[] inp, byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(inp, 0, 8);
    }

    public byte[] Dec(byte[] inp) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        return cipher.doFinal(inp, 0, 8);
    }

    //Added by Chetan
    public byte[] Dec(byte[] inp, byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        return cipher.doFinal(inp, 0, 8);
    }
    
    public byte[] enc(byte[] input) throws Exception {
        return Enc(Normalize(input), key);
    }

    private byte[] Normalize(byte[] inp) {
        if ((inp.length % 8) == 0) {
            return inp;
        }
        byte[] buffer = new byte[(inp.length + 8) - (inp.length % 8)];
        for (int i = 0; i < inp.length; i++) {
            buffer[i] = inp[i];
        }
        return buffer;
    }

    private byte[] SubByte(byte[] inp, int index) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            buffer[i] = inp[i + (index * 8)];
        }
        return buffer;
    }

    private byte[] XOR(byte[] b1, byte[] b2) {
        byte[] buffer = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            buffer[i] = (byte) (b1[i] ^ b2[i]);
        }
        return buffer;
    }

    private byte[] extractBytes(ISOMsg msg) throws ISOException {
        byte[] tmp = msg.pack();
        byte[] t = new byte[tmp.length - 16];
        System.arraycopy(tmp, 0, t, 0, t.length);
        return t;
    }

    public static void main(String[] args) throws Exception {
//        byte[] enc=new byte[]{(byte)0xCB,0x77,(byte)0xB1,0x68,0x45,(byte)0x96,0x16,0x10};
//        byte[] enc = new byte[]{0x5B, (byte) 0xEC, 0x0B, 0x58, 0x4F, (byte) 0x87, 0x27, (byte) 0xC8};
//        byte[] enc = new byte[]{0x76, (byte) 0x93, 0x1F, (byte) 0xAC, (byte) 0x9D, (byte) 0xAB, 0x2B, 0x36};
//        byte[] enc = new Mac().key;
        byte[] enc = new byte[]{0x36, 0x19, (byte) 0x83, (byte) 0x94, 0x31, (byte) 0xE1, 0x25, 0x0E};
        byte[] dec = new Mac1().Dec(enc);
        int i = 0;
    }
}



/*package com.en.datavsn.EFTswitch.atm.common;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.List;

*//**
 * Created with IntelliJ IDEA.
 * User: roohi
 * Date: 9/8/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 *//*
public class Mac {

    private byte[] key = new byte[]{0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C};


    public static final Mac instance = new Mac();

    public byte[] calcMac(ISOMsg msg, byte[] shetabKey) throws ISOException {
        byte[] input = extractBytes(msg);

        input = this.Normalize(input);
        byte[] buffer = new byte[8];
        try {
            for (int i = 0; (i * 8) < input.length; i++) {
                byte[] buffer2 = this.SubByte(input, i);
                byte[] inp = this.XOR(buffer, buffer2);
                buffer = this.Enc(inp,shetabKey);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return buffer;
    }

    private byte[] strToBytes(String s) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(i * 2, (i + 1) * 2), 16);
        }
        return bytes;
    }

    public byte[] pinEnc(String pin, String pan, byte[] shetabKey) {
        try {
            String str1 = String.valueOf(pin.length());
            if (str1.length() < 2)
                str1 = "0" + str1;
            str1 = str1 + pin;
            while (str1.length() < 16)
                str1 = str1 + "F";
            String str2 = "0000" + pan.substring(3, 15);

            byte[] beforEnc = XOR(strToBytes(str1), strToBytes(str2));
            beforEnc = Normalize(beforEnc);
            return Enc(beforEnc,shetabKey);
        } catch (Exception x) {
            x.printStackTrace();

        }
        return new byte[8];
    }

    private byte[] Enc(byte[] inp, byte[] shetabKey) throws Exception {
        DESKeySpec dks = new DESKeySpec(shetabKey);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(inp, 0, 8);
    }

    public byte[] decypt(byte[] inp, byte[] shetabKey) throws Exception {
        DESKeySpec dks = new DESKeySpec(shetabKey);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        return cipher.doFinal(inp, 0, 8);
    }
    
    public byte[] enc(byte[] input, byte[] shetabKey) throws Exception {
        return Enc(Normalize(input),shetabKey);
    }

    private byte[] Normalize(byte[] inp) {
        if ((inp.length % 8) == 0) {
            return inp;
        }
        byte[] buffer = new byte[(inp.length + 8) - (inp.length % 8)];
        for (int i = 0; i < inp.length; i++) {
            buffer[i] = inp[i];
        }
        return buffer;
    }

    private byte[] SubByte(byte[] inp, int index) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            buffer[i] = inp[i + (index * 8)];
        }
        return buffer;
    }

    private byte[] XOR(byte[] b1, byte[] b2) {
        byte[] buffer = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            buffer[i] = (byte) (b1[i] ^ b2[i]);
        }
        return buffer;
    }

    private byte[] extractBytes(ISOMsg msg) throws ISOException {
        byte[] tmp = msg.pack();
        byte[] t = new byte[tmp.length - 16];
        System.arraycopy(tmp, 0, t, 0, t.length);
        return t;
    }
}*/
