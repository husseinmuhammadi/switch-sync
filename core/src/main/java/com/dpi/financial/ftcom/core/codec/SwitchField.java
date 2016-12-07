package com.dpi.financial.ftcom.core.codec;

import com.dpi.financial.ftcom.core.hsmutil.MapList;

import java.util.HashMap;


public class SwitchField implements IField{
    private MapList valueMap;
    private HashMap<Object,IField> sw=new HashMap<Object, IField>();
    private Object key;
	public SwitchField(Object key,Object... names){
		//minimum size is 3
		//
		this.key=key;
		int size=names.length;
		int loops=size/2;
		 for (int x=0;x<loops;x++) {
			 Object cval=names[2*x];
			 IField parsec=(IField)names[2*x+1]; //Should be of Field
	         sw.put(cval, parsec);
			 //  System.out.println("condition:" +names[2*x] + ":"+names[2*x+1]);
	      }
	}

	public int unpack(byte[] message) {
		//Find the right parser
		//if(key instanceof String){//use equals
		//}else{
		//}
		Object keyValue=valueMap.getValue(key);
		IField parser=sw.get(keyValue);
		return parser.unpack(message);
	}

	public int unpack(byte[] message, int position) {
		Object keyValue=valueMap.getValue(key);
		IField parser=sw.get(keyValue);
		if(parser==null) throw new RuntimeException("Command "+keyValue+" not defined");
		parser.setValueMap(valueMap);
		return parser.unpack(message,position);
	}

	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValue(String value) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] pack() {
		//we know the key,g
		Object keyValue=valueMap.getValue(key); //get actual parser now
		
		IField parser=sw.get(keyValue);
		if(parser==null) throw new RuntimeException("Command "+keyValue+" not defined");
		parser.setValueMap(valueMap);
		return parser.pack();
	 
	}

	public void setValueMap(MapList valueMap) {
		this.valueMap=valueMap;
		
	}

}
/* 
 * 
 * Parser pa=Struct();
 * 
 * 
 * Every 3 arg should be Paser
 * Switch("a",2,pa,3,pb,"c",pc);
 *        
 * 
 */
