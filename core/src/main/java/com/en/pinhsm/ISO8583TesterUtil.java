package com.en.pinhsm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ISO8583TesterUtil {
	
	private Properties properties = null;

	private static ISO8583TesterUtil instance = new ISO8583TesterUtil();

	private ISO8583TesterUtil() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("./keys.properties"));
		} 
		catch (Exception e) {
		} 
	}
	
	public static ISO8583TesterUtil getInstance(){
		return instance;
	}
	
	public String getCC1(){
		return getProperty("key1");
	}
	
	public String getCC2(){
		return getProperty("key2");
	}
	
	public String getCC3(){
		return getProperty("key3");
	}
	
	public String getZPK(){
		return getProperty("ZPK");
	}

	public String getShetabPinKey(){
		return getProperty("ShetabPinKey");
	}

	public String getProperty(String key){
		if(key==null)
			return null;
		return properties.getProperty(key);
	}

	public String getTMK1(){
		return getProperty("TMK1");
	}
	
	public String getTMK2(){
		return getProperty("TMK2");
	}
	
	public String getTMK3(){
		return getProperty("TMK3");
	}
	
	public String getxTPK(){
		return getProperty("XTPK");
	}
}
