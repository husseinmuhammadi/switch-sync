package com.dpi.financial.ftcom.core.codec;

import java.util.ArrayList;
/*
 * It is a variable field with GS for its field separator
 */

public class RepeatField extends VariableField
{
	private int		count;
 	private char	gs;
 	ArrayList<String> data=new ArrayList<String>();
//	private IField	field_container;

	public RepeatField(String name,int loop_count,
		char fs,char gs)
	{
		//this.name = name;
		super(name,100,fs);
		this.count = loop_count;
		this.gs = gs;
		//this.field_container = container;
	}
    /*
     * Dont allow a call 
     *  */
	public ArrayList<String> getValue()
	{
		return data;
	}
	/*
	 * Dont allow call, now set value is same as addValueToList
	 *  */
	public void setValue(String value) {
		data.add(value);
	}
    
	public int unPack(byte[] message)
	{
		return unPack(message,0);
	}

	/*
	 * */
	public int unPack(byte[] message, int position)
	{
		int newposition=super.unpack(message, position);
		String val=(String)super.getValue();
		  String re[]=val.split(new String(new char[]{gs}));
	        for(String r:re){
	        	//System.out.println(r);
	        	data.add(r);
	        }
		//now divide the string using gs
		/*for (Iterator<IField> iter = fields.iterator(); iter.hasNext();)
		{
			IField field = ((IField)iter.next());
			position = field.unPack(message, position);
			map.put(field.getName(),field.getValue());
			System.out.println(field.toString());
		}*/
		return newposition;
	}
	
	public  String toString()
	{
		return " [" + count + "] :" ;
		
	}

	

	public byte[] pack() {
		StringBuffer sb=new StringBuffer();
		for(int x=0;x<data.size();x++){
			sb.append(data.get(x));
			if(x!=data.size()-1) sb.append(gs);
		}
		return sb.toString().getBytes();
	}
	public static void main(String args[]){
		byte[] test={0x31,0x32,0x0E,0x33,0x34,0x0E,0x36,0x38};
        String v=new String(test);
        String re[]=v.split(new String(new byte[]{0x0e}));
        for(String r:re){
        	System.out.println(r);
        }
	}
	
}
