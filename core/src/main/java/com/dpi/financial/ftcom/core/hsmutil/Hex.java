package com.dpi.financial.ftcom.core.hsmutil;

public class Hex {

	public static byte[] hex2byte (String s)  {
        return hex2byte (s.getBytes(), 0, s.length() >> 1);
    }
    public static byte[] hex2byte (byte[] b, int offset, int len)  {
        byte[] d = new byte[len];
        for (int i=0; i<len*2; i++) {
            int shift = i%2 == 1 ? 0 : 4;
            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
        }
        return d;
    } 
    public static String hexString(byte[] b)  {
        StringBuffer d = new StringBuffer(b.length * 2);
        for (int i=0; i<b.length; i++) {
            char hi = Character.forDigit ((b[i] >> 4) & 0x0F, 16);
            char lo = Character.forDigit (b[i] & 0x0F, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    } 
}
