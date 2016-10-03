package com.en.std.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class IranSystem {

	private final byte[] buffer;

	public IranSystem(byte[] binary) {
		buffer = Arrays.copyOf(binary, binary.length);
	}
	
	public IranSystem(String buffer) {
		this.buffer = new byte[buffer.length()];
		for (int index = 0; index < buffer.length(); index++)
			this.buffer[index] = (byte) buffer.charAt(index);
	}
	
	public byte[] getBytes() {
		return buffer;
	}

	/**
	 * This method use unicode characters for Iran System buffer
	 */
	public String getString() {
		return convert(buffer);
	}

	public ByteBuffer getBuffer() {
		return getBuffer("UTF-8");
	}
	
	public ByteBuffer getBuffer(String charsetName) {
		return Charset.forName(charsetName).encode(getString());
	}
	
	private String convert(byte[] bytes) {
        String buffer = "";
        for (int i = 0; i < bytes.length; i++) {
            byte val = bytes[i];
            char chr;
            switch (val) {
                case (byte) 128:
                    chr = '0';
                    break;
                case (byte) 129:
                    chr = '1';
                    break;
                case (byte) 130:
                    chr = '2';
                    break;
                case (byte) 131:
                    chr = '3';
                    break;
                case (byte) 132:
                    chr = '4';
                    break;
                case (byte) 133:
                    chr = '5';
                    break;
                case (byte) 134:
                    chr = '6';
                    break;
                case (byte) 135:
                    chr = '7';
                    break;
                case (byte) 136:
                    chr = '8';
                    break;
                case (byte) 137:
                    chr = '9';
                    break;
                case (byte) 138:
                    chr = '\u060c';
                    break;
                case (byte) 139:
                    chr = 0;
                    break;
                case (byte) 140:
                    chr = '\u061f';
                    break;
                case (byte) 141:
                    chr = '\u0622';
                    break;
                case (byte) 142:
                    chr = '\u0626';
                    break;
                case (byte) 143:
                    chr = '\u0621';
                    break;
                case (byte) 144:
                    chr = '\u0627';
                    break;
                case (byte) 145:
                    chr = '\u0627';
                    break;
                case (byte) 146:
                    chr = '\u0628';
                    break;
                case (byte) 147:
                    chr = '\u0628';
                    break;
                case (byte) 148:
                    chr = '\u067e';
                    break;
                case (byte) 149:
                    chr = '\u067e';
                    break;
                case (byte) 150:
                    chr = '\u062a';
                    break;
                case (byte) 151:
                    chr = '\u062a';
                    break;
                case (byte) 152:
                    chr = '\u062b';
                    break;
                case (byte) 153:
                    chr = '\u062b';
                    break;
                case (byte) 154:
                    chr = '\u062c';
                    break;
                case (byte) 155:
                    chr = '\u062c';
                    break;
                case (byte) 156:
                    chr = '\u0686';
                    break;
                case (byte) 157:
                    chr = '\u0686';
                    break;
                case (byte) 158:
                    chr = '\u062d';
                    break;
                case (byte) 159:
                    chr = '\u062d';
                    break;
                case (byte) 160:
                    chr = '\u062e';
                    break;
                case (byte) 161:
                    chr = '\u062e';
                    break;
                case (byte) 162:
                    chr = '\u062f';
                    break;
                case (byte) 163:
                    chr = '\u0630';
                    break;
                case (byte) 164:
                    chr = '\u0631';
                    break;
                case (byte) 165:
                    chr = '\u0632';
                    break;
                case (byte) 166:
                    chr = '\u0698';
                    break;
                case (byte) 167:
                    chr = '\u0633';
                    break;
                case (byte) 168:
                    chr = '\u0633';
                    break;
                case (byte) 169:
                    chr = '\u0634';
                    break;
                case (byte) 170:
                    chr = '\u0634';
                    break;
                case (byte) 171:
                    chr = '\u0635';
                    break;
                case (byte) 172:
                    chr = '\u0635';
                    break;
                case (byte) 173:
                    chr = '\u0636';
                    break;
                case (byte) 174:
                    chr = '\u0636';
                    break;
                case (byte) 175:
                    chr = '\u0637';
                    break;
                case (byte) 224:
                    chr = '\u0638';
                    break;
                case (byte) 225:
                    chr = '\u0639';
                    break;
                case (byte) 226:
                    chr = '\u0639';
                    break;
                case (byte) 227:
                    chr = '\u0639';
                    break;
                case (byte) 228:
                    chr = '\u0639';
                    break;
                case (byte) 229:
                    chr = '\u063a';
                    break;
                case (byte) 230:
                    chr = '\u063a';
                    break;
                case (byte) 231:
                    chr = '\u063a';
                    break;
                case (byte) 232:
                    chr = '\u063a';
                    break;
                case (byte) 233:
                    chr = '\u0641';
                    break;
                case (byte) 234:
                    chr = '\u0641';
                    break;
                case (byte) 235:
                    chr = '\u0642';
                    break;
                case (byte) 236:
                    chr = '\u0642';
                    break;
                case (byte) 237:
                    chr = '\u06a9';
                    break;
                case (byte) 238:
                    chr = '\u06a9';
                    break;
                case (byte) 239:
                    chr = '\u06af';
                    break;
                case (byte) 240:
                    chr = '\u06af';
                    break;
                case (byte) 241:
                    chr = '\u0644';
                    break;
                case (byte) 242:
                    chr = '\ufefb';
                    break;
                case (byte) 243:
                    chr = '\u0644';
                    break;
                case (byte) 244:
                    chr = '\u0645';
                    break;
                case (byte) 245:
                    chr = '\u0645';
                    break;
                case (byte) 246:
                    chr = '\u0646';
                    break;
                case (byte) 247:
                    chr = '\u0646';
                    break;
                case (byte) 248:
                    chr = '\u0648';
                    break;
                case (byte) 249:
                    chr = '\u0647';
                    break;
                case (byte) 250:
                    chr = '\u0647';
                    break;
                case (byte) 251:
                    chr = '\u0647';
                    break;
                case (byte) 252:
                    chr = '\u06cc';
                    break;
                case (byte) 253:
                    chr = '\u06cc';
                    break;
                case (byte) 254:
                    chr = '\u06cc';
                    break;
                default:
                    // modified by Hossein w.r.t bug #12212 as on Monday, November 09, 2015 since ver 2.1.6-P1
                    chr = (char) val;
                    break;
            }

            buffer += chr;

        }
        return buffer;
    }

}
