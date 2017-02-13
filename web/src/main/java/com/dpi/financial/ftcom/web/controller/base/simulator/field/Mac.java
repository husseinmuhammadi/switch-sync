package com.dpi.financial.ftcom.web.controller.base.simulator.field;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * Created by srmo6 on 6/14/2016.
 */
public class Mac {

    public byte[] key = new byte[]{0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C, 0x1C};
    //87A420BDAEBC3C47

    public static final Mac instance = new Mac();

    public byte[] calcMac(ISOMsg msg) throws ISOException {
        return calcMac(msg, key);
    }

    public byte[] calcMac(ISOMsg msg, byte[] key) throws ISOException {
        byte[] input = extractBytes(msg);

        input = this.Normalize(input);
        byte[] buffer = new byte[8];
        try {
            for (int i = 0; (i * 8) < input.length; i++) {
                byte[] buffer2 = this.SubByte(input, i);
                byte[] inp = this.XOR(buffer, buffer2);
                buffer = this.Enc(inp, key);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return buffer;
    }

    public byte[] calcMac(byte[] input, byte[] key) {

        input = this.Normalize(input);
        byte[] buffer = new byte[8];
        byte[] k1 = this.SubByte(key, 0);
        byte[] k2 = this.SubByte(key, 1);
        try {
            for (int i = 0; (i * 8) < input.length; i++) {
                byte[] buffer2 = this.SubByte(input, i);
                byte[] inp = this.XOR(buffer, buffer2);
                buffer = this.Enc(inp, k1);
            }
            buffer = this.Dec(buffer, k2);
            buffer = this.Enc(buffer, k1);
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

    public String extranctPin(String pan, String pinBlock, byte[] key, boolean aes) throws Exception {
        byte[] pinb = strToBytes(pinBlock);
        String str2 = "0000" + pan.substring(pan.length() - 13, pan.length() - 1);
        byte[] bytes = null;
        if (aes) {
            bytes = decAes(key, pinb);
        } else {
            bytes = dec3des(key, pinb);
        }
        byte[] bb = XOR(strToBytes(str2), bytes);
        String s = ISOUtil.hexString(bb);
        s = s.replace("F", "");
        s = s.substring(2);
        return s;
    }

    public byte[] pinEnc(String pin, String pan, byte[] key, boolean aes) {
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
            if (aes) {
                if (beforEnc.length < 16) {
                    byte[] tmp = new byte[16];
                    System.arraycopy(beforEnc, 0, tmp, 0, beforEnc.length);
                    beforEnc = tmp;
                }
                return encAes(key, beforEnc);
            } else {
                return enc3des(key, beforEnc);
            }

        } catch (Exception x) {
            x.printStackTrace();

        }
        return new byte[8];
    }

    public byte[] encCvv2(String cvv2, byte[] key, boolean aes) {
        try {
            while (cvv2.length() < 4)
                cvv2 += " ";
            byte[] tmp = new byte[16];
            new Random().nextBytes(tmp);
            System.arraycopy(cvv2.getBytes(), 0, tmp, 0, 4);
            if (aes) {
                return encAes(key, tmp);
            } else {
                return enc3des(key, tmp);
            }

        } catch (Exception x) {
            x.printStackTrace();

        }
        return new byte[16];
    }

    public byte[] enc(String plain, byte[] key, boolean aes) {
        try {
            if (aes) {
                return encAes(key, plain.getBytes());
            } else {
                return enc3des(key, plain.getBytes());
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return new byte[16];
    }

    public byte[] enc3des(byte[] keyBytes, byte[] plainTextBytes) throws Exception {
        if (keyBytes.length == 16) {
            byte[] buff = new byte[24];
            System.arraycopy(keyBytes, 0, buff, 0, 16);
            System.arraycopy(keyBytes, 0, buff, 16, 8);
            keyBytes = buff;
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
//        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = cipher.doFinal(plainTextBytes);
        return cipherText;
    }

    public byte[] dec3des(byte[] keyBytes, byte[] plainTextBytes) throws Exception {
        if (keyBytes.length == 16) {
            byte[] buff = new byte[24];
            System.arraycopy(keyBytes, 0, buff, 0, 16);
            System.arraycopy(keyBytes, 0, buff, 16, 8);
            keyBytes = buff;
        }
        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
//        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] cipherText = cipher.doFinal(plainTextBytes);
        return cipherText;
    }

    public String encTrack2(byte[] key, String track2, boolean aes) throws Exception {
        String plain = track2;
        String other = "";
        if (plain.length() > 32) {
            other = plain.substring(0, plain.length() - 32);
            plain = plain.substring(plain.length() - 32, plain.length());
        }
        while (plain.length() < 32) {
            plain = "E" + plain;
        }
        plain = plain.replace("=", "F");
        byte[] plainbytes = ISOUtil.hex2byte(plain);
        byte[] enced;
        if (aes)
            enced = encAes(key, plainbytes);
        else
            enced = enc3des(key, plainbytes);

        return other + ISOUtil.hexString(enced);
    }

    public String decTrack2(byte[] key, String track2, boolean aes) throws Exception {
        String enced = track2;
        String other = "";
        if (enced.length() > 32) {
            other = enced.substring(0, enced.length() - 32);
            enced = enced.substring(enced.length() - 32, enced.length());
        }
        byte[] encedbytes = ISOUtil.hex2byte(enced);
        byte[] plain;
        if (aes)
            plain = decAes(key, encedbytes);
        else
            plain = dec3des(key, encedbytes);
        String plainStr = ISOUtil.hexString(plain);
        while (plainStr.startsWith("E"))
            plainStr = plainStr.substring(1);
        plainStr = plainStr.replace("F", "=");
        return other + plainStr;
    }

    private byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data)
            throws Exception {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] result = new byte[actualLength];
        System.arraycopy(outBuf, 0, result, 0, result.length);
        return result;
    }

    public byte[] decAes(byte[] key, byte[] cipher) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), new byte[16]);
        aes.init(false, ivAndKey);
        return cipherData(aes, cipher);
    }

    public byte[] encAes(byte[] key, byte[] plain) throws Exception {
        PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                new AESEngine()));
        CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key), new byte[16]);
        aes.init(true, ivAndKey);
        return cipherData(aes, plain);
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
        System.arraycopy(inp, index * 8, buffer, 0, 8);
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


    public byte[] calcMacV7(ISOMsg msg, byte[] key) throws ISOException {
        byte[] input = extractBytesV7(msg);
        byte[] k1 = this.SubByte(key, 0);
        byte[] k2 = this.SubByte(key, 1);
        input = this.Normalize(input);
        byte[] buffer = new byte[8];
        try {
            for (int i = 0; (i * 8) < input.length; i++) {
                byte[] buffer2 = this.SubByte(input, i);
                byte[] inp = this.XOR(buffer, buffer2);
                buffer = this.Enc(inp, k1);
            }
            buffer = this.Dec(buffer, k2);
            buffer = this.Enc(buffer, k1);
            for (int i = 0; i < 4; i++) {
                buffer[i + 4] = 0;
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return buffer;
    }

    int[] macBits = new int[]{2, 3, 4, 6, 7, 10, 11, 12, 13, 15, 17, 22, 25, 32, 33, 37, 39, 41, 42, 48, 49, 51, 60, 61, 62, 66, 70, 90, 95, 96, 97, 99};

    public byte[] extractBytesV7(ISOMsg msg) {
        byte[] buffer = new byte[0];
        for (int i = 0; i < macBits.length; i++) {
            byte[] bitVal = msg.getBytes(macBits[i]);
            if (bitVal != null)
                buffer = ISOUtil.concat(buffer, bitVal);
        }
        return buffer;
    }


}
