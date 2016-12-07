package com.dpi.financial.ftcom.core.hsmutil;

import java.io.FileInputStream;
import java.util.Properties;


public class HSMErrorCodes {
	private Properties properties = null;
	private static HSMErrorCodes instance = new HSMErrorCodes();
	private HSMErrorCodes() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("./q2/cfg/hsmRes.properties"));
			
		} 
		catch (Exception e) {
		} 
	}
	
	public static HSMErrorCodes getInstance(){
		return instance;
	}
	
	public String getError(String errorCode){
		return getProperty(errorCode);
	}
	
	public String getProperty(String key){
		if(key==null)
		{
			return null;
		}
		return properties.getProperty(key);
	}
}