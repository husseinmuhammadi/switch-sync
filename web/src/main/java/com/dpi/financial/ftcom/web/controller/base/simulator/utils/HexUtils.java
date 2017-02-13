package com.dpi.financial.ftcom.web.controller.base.simulator.utils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class HexUtils {
	public static byte[] hexStringToByteArray(String hexString) {
	    int len = hexString.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
	                             + Character.digit(hexString.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static byte[] hexToBytes(String hexString) {
	     HexBinaryAdapter adapter = new HexBinaryAdapter();
	     byte[] bytes = adapter.unmarshal(hexString);
	     return bytes;
	}
}
