package com.dpi.financial.ftcom.core.hsmutil;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

/**
 * @author Olive
 */
public class ArrayUtil {
    /**
     * Bitwise XOR between corresponding bytes
     * 
     * @param op1
     *            byteArray1
     * @param op2
     *            byteArray2
     * @return an array of length = the smallest between op1 and op2
     */
    public static byte[] xor(byte[] op1, byte[] op2) {
        //make 2 arrays equal .. left justify with pad 0
        byte[] result;
        if (op1.length > op2.length) {
            byte[] a1 = new byte[op1.length];
            System.arraycopy(op2, 0, a1, 0, op2.length);
            return xor(op1, a1);
        } else if (op2.length > op1.length) {
            byte[] a1 = new byte[op2.length];
            System.arraycopy(op1, 0, a1, 0, op1.length);
            return xor(a1, op2);
        }
        result = new byte[op1.length];
        for (int i = 0; i < op1.length; i++) {
            result[i] = (byte) (op1[i] ^ op2[i]);
        }
        return result;

    }

    /**
     * Bitwise XOR between corresponding bytes
     * 
     * @param op1
     *            byteArray1
     * @param op2
     *            byteArray2
     * @param op3
     *            byteArray2
     * @return an array of length = the smallest between op1 and op2
     */
    public static byte[] xor(byte[] op1, byte[] op2, byte[] op3) {
        return xor(op1, xor(op2, op3));
    }

    public static final byte[] concat(byte abyte0[], byte abyte1[]) {
        byte[] ai = new byte[abyte0.length + abyte1.length];
        System.arraycopy(abyte0, 0, ai, 0, abyte0.length);
        System.arraycopy(abyte1, 0, ai, abyte0.length, abyte1.length);
        return ai;
    }

    public static final byte[] concat(byte abyte0[], byte abyte1[],
            byte[] abyte2) {
        byte[] ai = new byte[abyte0.length + abyte1.length];
        System.arraycopy(abyte0, 0, ai, 0, abyte0.length);
        System.arraycopy(abyte1, 0, ai, abyte0.length, abyte1.length);
        return concat(ai, abyte2);
    }

    /**
     * Concatenates two byte arrays
     * 
     * @param array1
     *            first array
     * @param array2
     *            second array
     * @return array1+array2
     */
    public static byte[] merge(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return (byte[]) array2.clone();
        } else if (array2 == null) {
            return (byte[]) array1.clone();
        } else {
            byte[] result = new byte[array1.length + array2.length];

            System.arraycopy(array1, 0, result, 0, array1.length);
            System.arraycopy(array2, 0, result, array1.length, array2.length);

            return result;
        }
    }

    /**
     * Returns a new array that is a part of an array. <br>
     * The new array begins at the specified begin index and extends to the byte
     * at index endIndex - 1. Thus the length of the new array is endIndex -
     * beginIndex.
     * 
     * @param array
     *            an array of bytes
     * @param beginIndex
     *            the begin index of the part to extract, inclusive
     * @param endIndex
     *            the end index of the part to extract, exclusive
     * @return the specified new array
     */
    public static byte[] subarray(byte[] array, int beginIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        byte[] result = new byte[endIndex - beginIndex];
        System.arraycopy(array, beginIndex, result, 0, result.length);
        return result;
    }

    public static final void split(byte abyte0[], byte left[], byte[] right) {

        System.arraycopy(abyte0, 0, left, 0, abyte0.length / 2);
        System.arraycopy(abyte0, abyte0.length / 2, right, 0,abyte0.length / 2);
        //return null;
    }

    public static int[] bArray(int value, int b, int h) {
        int[] a = new int[h];
        int i = 8;
        int tempValue = value;
        do {
            a[--i] = tempValue % b;
        } while ((tempValue = tempValue / b) > 0);
        return a;
    }

    public static void setOddParity(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            bytes[i] = (byte)(b & 0xfe |(b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 1) & 1);
        }
    }
    public static void setEvenParity(byte bytes[])
    {
        for(int i = 0; i < bytes.length; i++)
        {
            int b = bytes[i];
            bytes[i] = (byte)(b & 0xfe | (b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 0) & 1);
        }

    }
    
    public static byte[] hex2byte (byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i=0; i<len*2; i++) {
            int shift = i%2 == 1 ? 0 : 4;
            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
        }
        return d;
    }

    public static byte[] hex2byte (String s) {
        if (s.length() % 2 == 0) {
            return hex2byte (s.getBytes(), 0, s.length() >> 1);
        } else {
        	// Padding left zero to make it even size #Bug raised by tommy
        	return hex2byte("0"+s);
        }
    }

    public static String hexString(byte[] b) {
        StringBuffer d = new StringBuffer(b.length * 2);
        for (int i=0; i<b.length; i++) {
            char hi = Character.forDigit ((b[i] >> 4) & 0x0F, 16);
            char lo = Character.forDigit (b[i] & 0x0F, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }
    
    public static String zeroPad(String value, int len) {
        return doPad(value, len, '0');
     }

     public static String spacePad(String value, int len) {
        return doPad(value, len, ' ');
     }

     public static String doPad(String value, int len, char padChar) {
        int length = value.length();
        if (length <= len - 1) {
           for (int i = 1; i <= (len - length); i++) {
              value = padChar + value;
           }
        }
        return value;
     }
     
     public static String padright(String s, int len, char c){
     	try {
     		return ISOUtil.padright(s, len, c);
     	} catch (ISOException e) {
     		return s;
     	}
		}
     
     public static String padleft(String s, int len, char c){
     	try {
     		return ISOUtil.padleft(s, len, c);
     	} catch (ISOException e) {
     		return s;
     	}
	}
}