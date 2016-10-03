package com.en.cardutil;


public class CardUtil {	 
  
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

	public static byte[] hex2byte (String s) {
		if (s.length() % 2 == 0) {
			return hex2byte (s.getBytes(), 0, s.length() >> 1);
		} else {
			// Padding left zero to make it even size #Bug raised by tommy
			return hex2byte("0"+s);
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

	public static String zeroPad(String value, int len) {
		return doPad(value, len, '0');
	}

	public static String doPad(String value, int len, char padChar) {
		String value1=null;
		int length = value.length();
		if (length <= len - 1) {
			for (int i = 1; i <= (len - length); i++) {
				value1 = padChar + value1;
			}
		}
		return value1;
	}

	public static byte[] checkMultiplesOfEight(byte[] data) {

		if(data.length % 8 != 0){			
			String sData = CardUtil.hexString(data);
			if(sData.length()<16){				
				sData = CardUtil.zeroPad(sData, 16);
				data = CardUtil.hex2byte(sData);
			}
			else if(sData.length()>16 && data.length<32){				
				sData = CardUtil.hexString(data);
				sData = CardUtil.zeroPad(sData, 32);
				data = CardUtil.hex2byte(sData);
			}
		}		
		return data;
	}
	
	public static String checkMode(byte[] result, int mode){
		String encCardNumber = null;
		if(mode==1){
			encCardNumber = CardUtil.hexString(result);
		}
		if(mode==2){
			encCardNumber = CardUtil.hexString(result);
			encCardNumber = encCardNumber.replaceFirst("^0+(?!$)", "");
			}
		return encCardNumber;
	}

	public static String[] validateInput(String[] createCardNumber)throws Exception{
		String[] returnValue = new String[4];
		String paddedProductCode;
		String paddedRunningCardNumber;

		if(createCardNumber[0].length()== 6){

			for(paddedProductCode = createCardNumber[1]; paddedProductCode.length()<3; paddedProductCode = "0"+paddedProductCode){
				//System.out.println("productCode : " + paddedProductCode);
			}

			paddedRunningCardNumber = createCardNumber[2];

			int productCardLength = Integer.parseInt(createCardNumber[3])-1;			
			int initialCardLength = createCardNumber[0].length()+paddedProductCode.length() + paddedRunningCardNumber.length();			

			while(initialCardLength<productCardLength){
				paddedRunningCardNumber = "0"+paddedRunningCardNumber;
				initialCardLength = createCardNumber[0].length()+paddedProductCode.length() + paddedRunningCardNumber.length();
			}			
			returnValue[0] = createCardNumber[0];
			returnValue[1] = paddedProductCode;
			returnValue[2] = paddedRunningCardNumber;
			returnValue[3] = createCardNumber[3];
		}
		else{
		  System.out.println("length of the issuer bin is less than six ,the length  is : "+createCardNumber[0].length());
	}
		return returnValue;		
	}

	/*public static void main(String[] args) {
		String cc[] = new String[3];
		cc[0] = "123456";
		cc[1] = "2";
		cc[2] = "399887";
		CardUtil.validateInput(cc);
	}*/


}
