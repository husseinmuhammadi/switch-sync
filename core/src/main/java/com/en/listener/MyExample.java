package com.en.listener;

import java.util.Properties;
import org.jpos.core.SimpleConfiguration;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.NACChannel;
import org.jpos.iso.packager.DummyPackager;
import org.jpos.iso.packager.ISO87BPackager;
import org.jpos.iso.packager.ISO93APackager;

import com.en.hsm.FSDMsg;


public class MyExample {
	private static final String REPORT_NAME = "jeremia.rpt";

	public static void main(String[] args) throws Exception{
	
			    try {
	        NACChannel hsm=new NACChannel("192.168.134.57",6999,new ISO93APackager(), new byte[5]);
	        //hsm.setHost("192.168.134.57", 6999);
	        //hsm.setPackager(new ISO93APackager());
	        //hsm.setPackager(new DummyPackager());
	        hsm.setHeader(ISOUtil.hex2byte("01010101"));
	        //hsm.se
	        hsm.connect();
	     ISOMsg msg = new ISOMsg();
	     //msg.setPackager(new ISO93APackager());
	     msg.set(0, "1111");
	     msg.set(11, "222222");
	     msg.set(22, "333");
	     msg.dump(System.out, "");
	     hsm.send(msg);
	     
	     }catch(Exception e){
	         e.printStackTrace();
	     }
	    }
	


	
}
