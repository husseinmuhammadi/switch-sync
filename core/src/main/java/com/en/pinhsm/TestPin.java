package com.en.pinhsm;

import org.jpos.iso.ISOUtil;

public class TestPin {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 String  pan="456110000720";
		 String ref="2E08DEE23E1805C0";
		 String pin="1234";
	     String format="01";//Make it constant         
	     PinUtil pu=new PinUtil();
	     for(int x=0;x<9999;x++){
	    	 pin=ISOUtil.zeropad(""+x, 4);
	     String pb=pu.createPinBlock(pan, pin); 
         if(pb.equals(ref)){
	       System.out.println(x+":"+pb);
         }else{
        	//System.out.println("               "+pin); 
         }
	      	 
	    }
         
         
	}

}
