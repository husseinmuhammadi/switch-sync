package com.en.listener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

import org.jpos.iso.ISOUtil;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;

import org.jpos.util.Loggeable;
import org.jpos.util.Logger;



public class AtmPackager implements Loggeable, Cloneable{

	protected Logger logger = null;
	protected String realm = null;

	protected int direction;
	protected int msgType = 1;
	public static final int REQUEST = 1;
	public static final int RESPONSE = 2;

	public static final int INCOMING = 1;
	public static final int OUTGOING = 2;
	public static final char FS = '\034';
	public static final char GS = '\035';
	public static final char ETX = '\003';
	public static final char SYN = '\026';
	public static final char NUL = '\000';
	public static final char US = '\037';
	public static final char RS = '\036';
	public static final char EOF = '\000';
	public static final char PIPE = '\u007C';
	public static final char STX = '\002';

	private static final Set<String> DUMMY_SEPARATORS = new HashSet<String>(
			Arrays.asList("DS", "EOM"));

	Map map;
	String baseSchema;
	byte[] header;
	String basePath;
	Map separators;

	public AtmPackager(String basePath) {
		this(basePath, "base");
	}

	/**
	 * 
	 * @param basePath
	 * @param baseSchema
	 *
	 * @since 15 Sep 2013 by chetan wrt Issue#1536
	 */
	public AtmPackager(String basePath, String baseSchema) {
		super();
		map = new LinkedHashMap();
		separators = new LinkedHashMap();
		this.baseSchema = baseSchema;
		this.basePath = basePath;

		setSeparator("FS", FS);
		setSeparator("US", US);
		setSeparator("GS", GS);
		setSeparator("RS", RS);
		setSeparator("EOF", EOF);
		setSeparator("PIPE", PIPE);
		setSeparator("STX", STX);
		setSeparator("ETX", ETX);
	}
	
	public void unpack(InputStream is) throws IOException, JDOMException,
			MalformedURLException {
		try {
			unpack(is, getSchema(baseSchema));
		} catch (EOFException e) {
			map.put("EOF", "true");
		}
	}

	public void unpack(byte[] b) throws IOException, JDOMException,
			MalformedURLException {
		unpack(new ByteArrayInputStream(b));
	}

	public void setLogger(Logger logger, String realm) {
		this.logger = logger;
		this.realm = realm;
	}

	public String getRealm() {
		return realm;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getDescription() {
		return getClass().getName();
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * @since Ver 1.2 by chetan wrt Issue#1536 as on 15 Sep 2013
	 */
	public String pack() throws JDOMException, MalformedURLException,
			ISOException, IOException {
		StringBuffer sb = new StringBuffer();
		// baseSchema="command";
		System.out.println("inside packing method-----" + baseSchema);
		pack(getSchema(baseSchema), sb);
		return sb.toString();
	}

	protected String get(String id, String type, int length, String defValue)
			throws ISOException {

		String value = (String) map.get(id);
		if (value == null)
			value = defValue == null ? "" : defValue;

		type = type.toUpperCase();

		switch (type.charAt(0)) {
		case 'N':
			value = ISOUtil.zeropad(value, length);
			break;
		case 'A':
			value = ISOUtil.strpad(value, length);
			break;
		case 'K':
			if (defValue != null)
				value = defValue;
			break;
		}
		return (type.endsWith("FS")) ? value.trim() : value;
	}

	protected void pack(Element schema, StringBuffer sb) throws JDOMException,
			MalformedURLException, ISOException, IOException {
		String keyOff = "";
		Iterator iter = schema.getChildren("field").iterator();
		while (iter.hasNext()) {
			Element elem = (Element) iter.next();
			String id = elem.getAttributeValue("id");
			int length = Integer.parseInt(elem.getAttributeValue("length"));
			String type = elem.getAttributeValue("type");
			boolean key = "true".equals(elem.getAttributeValue("key"));
			String defValue = elem.getText();
			String value = get(id, type, length, defValue);

			sb.append(value);

			if (type.endsWith("FS"))
				sb.append(FS);
			else if (type.endsWith("GS"))
				sb.append(GS);
			else if (type.endsWith("STX"))
				sb.append(STX);
			else if (type.endsWith("ETX"))
				sb.append(ETX);

			if (key)
				keyOff = keyOff + value;
		}
		if (keyOff.length() > 0)
			pack(getSchema(getId(schema) + keyOff), sb);
	}

	protected void unpack(InputStream is, Element schema) throws IOException,
			JDOMException, MalformedURLException

	{
		System.out.println("in unpack---------------------------------------");
		Iterator iter = schema.getChildren("field").iterator();
		String keyOff = "";
		while (iter.hasNext()) {
			Element elem = (Element) iter.next();

			String id = elem.getAttributeValue("id");
			int length = Integer.parseInt(elem.getAttributeValue("length"));
			String type = elem.getAttributeValue("type").toUpperCase();
			boolean blType = false;
			if (type.endsWith("FS"))
				blType = true;
			else if (type.endsWith("GS"))
				blType = true;
			else if (type.endsWith("SYN"))
				blType = true;
			else if (type.endsWith("NULL"))
				blType = true;
			else if (type.endsWith("STX"))
				blType = true;
			else if (type.endsWith("ETX"))
				blType = true;
			boolean key = "true".equals(elem.getAttributeValue("key"));
			String value = readField(is, id, length, blType);

			if (key)
				keyOff = keyOff + value;

			if ("K".equalsIgnoreCase(type) && !value.equals(elem.getText()))
				throw new IllegalArgumentException("Field " + id + " value='"
						+ value + "' expected='" + elem.getText() + "'");
		}
		if (keyOff.length() > 0) {
			unpack(is, getSchema(getId(schema) + keyOff));
		}
		System.out.println("in unpack---------------------------------------");
	}

	private String getId(Element e) {
		String s = e.getAttributeValue("id");
		return s == null ? "" : s;
	}

	protected String read(InputStream is, int len, boolean fs)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[1];
		for (int i = 0; i < len; i++) {
			if (is.read(b) < 0) {
				if (fs) {
					fs = false;
					break;
				}
				break;
				// throw new EOFException ();
			} else if (b[0] == FS) {
				fs = false;
				break;
			} else if (b[0] == GS) {
				fs = false;
				break;
			} else if (b[0] == ETX) {
				fs = false;
				break;
			} else if (b[0] == SYN) {
				fs = false;
				break;
			} else if (b[0] == NUL) {
				fs = false;
				break;
			} else if (b[0] == STX) {
				fs = false;
				break;
			}
			sb.append((char) (b[0] & 0xff));
		}
		if (fs) {
			if (is.read(b) < 0)
				; // throw new EOFException ();
		}
		return sb.toString();
	}

	protected String readField(InputStream is, String fieldName, int len,
			boolean fs) throws IOException {
		String fieldValue = read(is, len, fs);
		map.put(fieldName, fieldValue);
		System.out.println(fieldName + ":" + fieldValue);
		return fieldValue;
	}

	public void set(String name, String value) {
		if (value != null)
			map.put(name, value);
		else
			map.remove(name);
	}

	public void setHeader(byte[] h) {
		this.header = h;
	}

	public byte[] getHeader() {
		return header;
	}

	public String getHexHeader() {
		return header != null ? ISOUtil.hexString(header).substring(2) : "";
	}

	public String get(String name) {
		return (String) map.get(name);
	}

	public String get(String name, String defaultValue) {
		String value = get(name);
		if (value == null)
			value = defaultValue;
		return value;
	}

	public void copy(String name, AtmPackager msg) {
		map.put(name, msg.get(name));
	}

	public byte[] getHexBytes(String name) {
		String s = get(name);
		return s == null ? null : ISOUtil.hex2byte(s);
	}

	public int getInt(String name) {
		int i = 0;
		try {
			i = Integer.parseInt(get(name));
		} catch (Exception e) {
		}
		return i;
	}

	public Element toXML() {
		Element e = new Element("message");
		if (header != null) {
			e.addContent(new Element("header").setText(getHexHeader()));
		}
		Iterator iter = map.keySet().iterator();
		try {
			while (iter.hasNext()) {
				String fieldName = (String) iter.next();
				Element inner = new Element(fieldName);
				inner.addContent((String) map.get(fieldName));
				e.addContent(inner);
			}
		} catch (Exception ex) {
			System.out.println("Exception in to xml" + ex.getMessage());
			ex.printStackTrace();
		}
		return e;
	}

	protected Element getSchema() throws JDOMException, MalformedURLException,
			IOException {
		return getSchema(baseSchema);
	}

	protected Element getSchema(String message) throws JDOMException,
			MalformedURLException, IOException {
		// StringBuffer sb = new StringBuffer
		// ("file:D:/jpos-1.6.2/build/cfg/ndc-");
		StringBuffer sb = new StringBuffer(this.basePath);
		sb.append(message);
		sb.append(".xml");
		String uri = sb.toString();

		System.out.println("Schema: " + uri);

		Space sp = SpaceFactory.getSpace();
		Element schema = (Element) sp.rdp(uri);
		if (schema == null) {
			synchronized (AtmPackager.class) {
				schema = (Element) sp.rdp(uri);
				if (schema == null) {
					SAXBuilder builder = new SAXBuilder();
					schema = builder.build(new URL(uri)).getRootElement();
				}
				sp.out(uri, schema);
			}
		}
		return schema;
	}

	public void dump(PrintStream p, String indent) {
		try {
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			out.output(toXML(), p);
			p.println("");
		} catch (IOException e) {
			e.printStackTrace(p);
		}
	}

	public Map getMap() {
		return map;
	}

	public Object clone() {
		try {
			AtmPackager m = (AtmPackager) super.clone();
			m.map = (Map) ((LinkedHashMap) map).clone();
			return m;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}

	}

	public boolean isSeparator(byte b) {
		return separators.containsValue(new Character((char) b));
	}

	/**
	 * Added by chetan wrt Issue#1536 as on 15 Sep 2013
	 * 
	 * add a new or override an existing separator type/char pair.
	 * @param separatorName string of type used in definition (FS, US etc)
	 * @param separator char representing type
	 */
	public void setSeparator(String separatorName, char separator) {
		separators.put(separatorName, new Character(separator));
	}

}