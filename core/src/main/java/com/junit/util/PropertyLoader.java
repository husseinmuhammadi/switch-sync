package com.junit.util;

import java.io.FileInputStream;
import java.util.Properties;


public class PropertyLoader {
	
	
	private Properties properties = null;

	private static PropertyLoader instance = new PropertyLoader();
	
	private PropertyLoader() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("./junit.properties"));
		} 
		catch (Exception e) {
		} 
	}
	
	public static PropertyLoader getInstance(){
		return instance;
	}
	
	public String getProperty(String key){
		if(key==null)
			return null;
		return properties.getProperty(key);
	}
}
