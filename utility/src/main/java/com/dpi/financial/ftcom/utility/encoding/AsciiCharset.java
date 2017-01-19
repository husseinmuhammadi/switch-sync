package com.dpi.financial.ftcom.utility.encoding;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by h.mohammadi on 12/25/2016.
 */
public class AsciiCharset {

    public String decode(byte[] ascii) {
        // return new String(ascii, StandardCharsets.US_ASCII);
        StringBuffer buffer = new StringBuffer();
        for (byte b : ascii)
            buffer.append(getAsciiSymbol(b));
        return buffer.toString();
    }

    /**
     * http://stackoverflow.com/questions/5688042/how-to-convert-a-java-string-to-an-ascii-byte-array
     *
     * @param charset
     * @return byte array in ascii
     */
    public byte[] encode(String charset) {
        return charset.getBytes(StandardCharsets.US_ASCII);
    }

    private String getAsciiSymbol(int i) {
        String symbol = "";

        switch (i) {
            case 0:
                symbol = "[NUL]";
                break;
            case 1:
                symbol = "[SOH]";
                break;
            case 2:
                symbol = "[STX]";
                break;
            case 3:
                symbol = "[ETX]";
                break;
            case 4:
                symbol = "[EOT]";
                break;
            case 5:
                symbol = "[ENQ]";
                break;
            case 6:
                symbol = "[ACK]";
                break;
            case 7:
                symbol = "[BEL]";
                break;
            case 8:
                symbol = "[BS]";
                break;
            case 9:
                symbol = "[HT]";
                break;
            case 10:
                symbol = "[LF]";
                break;
            case 11:
                symbol = "[VT]";
                break;
            case 12:
                symbol = "[FF]";
                break;
            case 13:
                symbol = "[CR]";
                break;
            case 14:
                symbol = "[SO]";
                break;
            case 15:
                symbol = "[SI]";
                break;
            case 16:
                symbol = "[DLE]";
                break;
            case 17:
                symbol = "[DC1]";
                break;
            case 18:
                symbol = "[DC2]";
                break;
            case 19:
                symbol = "[DC3]";
                break;
            case 20:
                symbol = "[DC4]";
                break;
            case 21:
                symbol = "[NAK]";
                break;
            case 22:
                symbol = "[SYN]";
                break;
            case 23:
                symbol = "[ETB]";
                break;
            case 24:
                symbol = "[CAN]";
                break;
            case 25:
                symbol = "[EM]";
                break;
            case 26:
                symbol = "[SUB]";
                break;
            case 27:
                symbol = "[ESC]";
                break;
            case 28:
                symbol = "[FS]";
                break;
            case 29:
                symbol = "[GS]";
                break;
            case 30:
                symbol = "[RS]";
                break;
            case 31:
                symbol = "[US]";
                break;
            default:
                symbol = Character.toString((char) i);
        }

        return symbol;
    }

    /**
     * http://www.concretepage.com/java/example-charset-java-nio
     */
    public void sample01() {
        Charset charset = Charset.forName("US-ASCII");
        System.out.println(charset.displayName());
        System.out.println(charset.canEncode());
        String s = "Hello, This is Charset Example.";
        //convert byte buffer in given charset to char buffer in unicode
        ByteBuffer bb = ByteBuffer.wrap(s.getBytes());
        CharBuffer cb = charset.decode(bb);
        //convert char buffer in unicode to byte buffer in given charset
        ByteBuffer newbb = charset.encode(cb);
        while (newbb.hasRemaining()) {
            char ch = (char) newbb.get();
            System.out.print(ch);
        }
        newbb.clear();
    }
}
