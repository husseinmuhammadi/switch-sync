package com.en.pinhsm;

import java.util.StringTokenizer;


public final class Conversion {

    /** Don't let anyone instantiate this class */
	private Conversion() {
	}
    /**
     * converts a byte array to hex string
     * (suitable for dumps and ASCII packaging of Binary fields
     * @param b - byte array
     * @return String representation
     */
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

	/**
	 * Convert a hex array representing a numerical value into its BCD format.
	 * The size of the input must be less than 5.
	 * @param the number in Hex format
	 * @return the number in BCD format
	 */
	public static byte[] HexArray2BCD(byte[] value) {
		int sizeHex = value.length; // length of Hex
		int sizeBCD = sizeHex + 1;
		return Long2BCD(HexArray2Long(value), sizeBCD);
	}

	/**
	 * Convert a BCD array representing a numerical value into its hex format.
	 * The size of the input must be less than 5.
	 * @param the number in BCD format
	 * @return the number in Hex format
	 */
	public static byte[] BCD2Hex(byte[] value) {
		return Long2Hex(BCD2Long(value), value.length);
	}

		/**
	 * Convert array with binary integer stored in big endian format to long integer
	 * @param value number to be converted in byte array
	 * @return long containing the numerical value of the byte array
	 */
	public static long HexArray2Long(byte[] value) {
		int length = value.length;
		long result = 0L;
		long digit;

		for (int i = 0; i < length; i++) {
			digit = value[i] & 0xFFL;
			result <<= 8;
			result |= digit;
		}

		return result;
	}

	/**
	 * Convert array with binary integer stored in big endian format to integer
	 * @param value number to be converted in byte array
	 * @return int containing the numerical value of the byte array
	 */
	public static int HexArray2Int(byte[] value) {
		int length = value.length;
		int result = 0;
		int digit;

		for (int i = 0; i < length; i++) {
			digit = value[i] & 0xFF;
			result <<= 8;
			result |= digit;
		}

		return result;
	}

	/**
	 * Convert a a byte array coded in BCD right justify to a long value.
	 * @param byte array coded in BCD to be converted
	 * @return value number
	 */
	public static long BCD2Long(byte[] value) {
		int length = value.length;
		long temp = 0;
		int digit100;

		for (int i = 0; i < length; i++) {
			digit100 = value[i] & 0xFF;
			// convert byte from BCD to digit range 0..99
			digit100 = 10 * (digit100 >>> 4) + (digit100 & 0xF);

			temp *= 100;
			temp += digit100;
		}

		return temp;
	}

	/** Convert a long value to binary array in big endian format
	 * @param value number to be converted,
	 * @param nbByte length of the byte array
	 * @return binary byte array in big endian, right justified
	 */
	public static byte[] Long2Hex(long value, int nbByte) {
		byte[] result = new byte[nbByte];
		while (nbByte > 0) {
			result[--nbByte] = (byte) (value & 0xFFL);
			value >>>= 8;
		}

		return result;
	}

	/** Convert a long value to a byte array coded in BCD right justify,
	* padded with zeroes.
	* @param value number to be converted, size of array and number
	* @return byte array coded in BCD
	*/
	public static byte[] Long2BCD(long value, int nbByte) {
		byte[] result = new byte[nbByte];
		while (nbByte > 0) {
			result[--nbByte] = Int2BCD((int) (value % 100));
			value /= 100L;
		}

		return result;
	}

	/**
	 * Convert byte array containing ASCII encoded digits to a byte array
	 * @param value array with decimal digits coded in ASCII.
	 * @return converted value.
	 */
	public static byte[] ASCII2Hex(byte[] value) {
		byte[] temp = new byte[value.length];
		for (int i = 0; i < value.length; i++) {
			temp[i] = (byte) ((value[i] & 0xFFL) - 0x30L);
		}
		return temp;
	}

	/**
     * @param   b       source byte array
     * @param   offset  starting offset
     * @param   len     number of bytes in destination (processes len*2)
     * @return  byte[len]
     */
    public static byte[] hex2byte (byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i=0; i<len*2; i++) {
            int shift = i%2 == 1 ? 0 : 4;
            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
        }
        return d;
    }
    /**
     * @param s source string (with Hex representation)
     * @return byte array
     */
    public static byte[] hex2byte (String s) {
        return hex2byte (s.getBytes(), 0, s.length() >> 1);
    }

	/**
	 * Convert string to integer
	 * @param value string to be converted
	 * @param base base to be used (binary, octal, decimal, hexadecimal)
	 * @return converted value
	 */
	public static int String2Integer(String value, int base) {
		int length = value.length();
		int temp = 0;
		int digit;

		for (int i = 0; i < length; i++) {
			digit = ( value.charAt(i)) & 0x7F;
			// Read byte and convert to ASCII7

			if (digit <= 0x39) // convert to digit
				digit -= 0x30;
			else
				digit = (digit & 0x4F) - 0x37;

			temp *= base;
			temp += digit;
		}
		return temp;
	}

	/**
	 * Convert integer value to byte coded in BCD
	 * @param value integer bo be converted, must be in range 0..99
	 * @return converted value coded in BCD
	 */
	public static byte Int2BCD(int value) {
		return (byte) (((value / 10) << 4) | (value % 10));
	}

	/** Convert a byte coded in BCD into an Int.
	* @param value byte bo be converted, must be in range 0..99
	* @return converted value coded in BCD
	*/
	public static byte Byte2BCD(byte value) {
		return (byte) (((value & 0xF0) >> 4) * 10 + (value & 0x0F));
	}

	/**
	 * Convert byte coded in BCD to integer
	 * @param value BCD value to be converted
	 * @return converted integer value
	 */
	public static int BCD2Int(byte value) {
		int digit100 = value & 0xFF;
		return 10 * (digit100 >>> 4) + (digit100 & 0xF);
	}

	/**
	 * Convert byte array containing decimal ASCII encoded digits to a byte array
	 * @param value array with decimal digits coded in ASCII.
	 * @return converted value.
	 */
	public static byte[] ASCII2BCD(byte[] value) {
		byte highnibble;
		byte lownibble;
		byte[] temp = null;

		if (value.length % 2 == 1) {
			temp = new byte[(value.length / 2) + 1];
		} else {
			temp = new byte[value.length / 2];
		}

		int j = 0;
		for (int i = 0; i < value.length; i++) {
			highnibble = (byte) ((value[i] & 0xFFL) - 0x30L);
			if (i + 1 < value.length) {
				lownibble = (byte) ((value[++i] & 0xFFL) - 0x30L);
			} else {
				lownibble = 0x0F;
			}
			temp[j] = (byte) ((highnibble << 4) | (lownibble));
			j++;
		}
		return temp;
	}
	/**
	 * Helper to create value field from array of object identifier elements
	 * 
	 * @param oid
	 * 		Array containing object identifier elements
	 */	
	protected static byte[] fromIntArray(int oid[]) {
		int i, j, size, val;
		
		if ((oid.length < 2) || (oid[0] < 0) || (oid[0] > 2) || (oid[1] < 0) || (oid[1] > 39))
			throw new IllegalArgumentException("Object identifier out of range");
			
		size = 1;
		
		for (i = 2; i < oid.length; i++) {
			if (oid[i] > 16384) {
				size += 3;
			} else if (oid[i] > 128) {
				size += 2;
			} else {
				size++;
			}
		}

		byte[] value = new byte[size];

		value[0] = (byte)(40 * oid[0] + oid[1]);
		
		j = 1;
		for (i = 2; i < oid.length; i++) {
			val = oid[i];
			
			if (val >= 0x4000) {
				value[j++] = (byte)((val >> 14) | 0x80);
				val &= 0x3FFF;
			}
			if (val >= 0x80) {
				value[j++] = (byte)((val >> 7) | 0x80);
				val &= 0x7F;
			}
			value[j++] = (byte)(val);		
		}
		return value;
	}


	
	/**
	 * Helper to create byte array from string
	 * 
	 * @param oid
	 */
	public static byte[] fromOIDString(String oid) {
        try {
            StringTokenizer sp = new StringTokenizer(oid, " .");
            int[] elements = new int[sp.countTokens()];
            int i = 0;
            while (sp.hasMoreTokens()) {
                String temp = sp.nextToken();
                elements[i++] = Integer.parseInt(temp);
            }
            // Call the helper function to create the actual byte buffer
             return  fromIntArray(elements);
        }
        catch(NumberFormatException nfe) {
			throw new IllegalArgumentException("Object identifier string is invalid");
        }
	}
	
	   public static String escape(String text){
		   text=text.replaceAll("<", "&lt;");
		   text=text.replaceAll(">", "&gt;");
		   text=text.replaceAll("\n", "<br>");
		   return text;
	   }
		
}
