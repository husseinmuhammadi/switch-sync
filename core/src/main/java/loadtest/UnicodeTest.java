package loadtest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
public class UnicodeTest {

	 public static void printBytes(byte[] array, String name) {
		 System.out.println("----------------");
		    for (int k = 0; k < array.length; k++) {
		      System.out.println(name + "[" + k + "] = " + "0x"
		          + UnicodeFormatter.byteToHex(array[k]));
		    }
		  }

		  public static void main(String[] args) {

		    System.out.println(System.getProperty("file.encoding"));
		    String original = new String("\u0020\u0645\u0633\u06a9\u0646\u0020\u0628\u0627\u0020\u06a9\u0627\u0631\u062a");

		    try {
		    
		      File file = new File ("E:\\ABC.txt");
		      FileOutputStream fos = new FileOutputStream(file);
		      System.out.println("original = " + original);

		      fos.write(original.getBytes());

		      byte[] utf8Bytes = original.getBytes("UTF8");
		      byte[] defaultBytes = original.getBytes();

		      String roundTrip = new String(utf8Bytes, "UTF8");
		      System.out.println("roundTrip = " + roundTrip);


		      
		      fos.write(utf8Bytes);
		      
		      System.out.println();
		      printBytes(utf8Bytes, "utf8Bytes");
		      System.out.println();
		      printBytes(defaultBytes, "defaultBytes");
		    } catch (Exception e) {
		      e.printStackTrace();
		    }

		  } // main

		}

		class UnicodeFormatter {

		  static public String byteToHex(byte b) {
		    // Returns hex String representation of byte b
		    char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		        'a', 'b', 'c', 'd', 'e', 'f' };
		    char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		    return new String(array);
		  }

		  static public String charToHex(char c) {
		    // Returns hex String representation of char c
		    byte hi = (byte) (c >>> 8);
		    byte lo = (byte) (c & 0xff);
		    return byteToHex(hi) + byteToHex(lo);
		  }

}
