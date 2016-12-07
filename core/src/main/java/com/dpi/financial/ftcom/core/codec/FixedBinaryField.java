package com.dpi.financial.ftcom.core.codec;

import com.dpi.financial.ftcom.core.hsmutil.Hex;
import com.dpi.financial.ftcom.core.hsmutil.MapList;

public class FixedBinaryField implements IField {
    private String name;
    private int size;
    //private String	expected;
    //private byte[]	value;
    private MapList valueMap;

    public FixedBinaryField(String name, String data) {
        this.name = name;
        this.size = data.length();
        //value = data.getBytes();//new byte[this.size];
        //this.value = ISOUtil.hex2byte(data);
    }

    public FixedBinaryField(String name, int i) {
        this.name = name;
        this.size = i;
        //value = new byte[this.size];

    }

    /* (non-Javadoc)
     * @see StreamParse.IField1#parse(byte[])
     */
    public int unpack(byte[] message) {
        return unpack(message, 0);
    }


    /* (non-Javadoc)
     * @see StreamParse.IField1#parse(byte[], int)
     */
    public int unpack(byte[] message, int position) {
        byte[] value = new byte[size];
        int i = 0;
        for (i = 0; i < size; i++, position++) {
            value[i] = message[position];
        }
        valueMap.putValue(name, Hex.hexString(value));

        return position;
    }

    public String toString() {
        String value = (String) valueMap.getValue(name);
        return " [" + size + "] :" + new String(value);
    }


    /* (non-Javadoc)
     * @see StreamParse.IField1#getValue()
     */
    public String getValue() {
        String value = (String) valueMap.getValue(name);
        return value;
    }

    public void setValue(String value) {
        //this.value=value.getBytes();
        valueMap.putValue(name, value);
    }

    public String getName() {
        return name;
    }


	 
/*
    public String getField(String name)
	{
		// TODO Auto-generated method stub
		return (String)map.get(name);
	}*/


    public byte[] pack() {
        //return value;//.getBytes();// using size, need to pack
        String v = (String) valueMap.getValue(name);
        //use suitable padding
        //return v.getBytes();
        return Hex.hex2byte(v);
    }


    public void setValueMap(MapList valueMap) {
        this.valueMap = valueMap;

    }
	
/*	public static void main(String[] args)
	{
		String test1_str = "hello";
		String test2_str = "1234588888888888888888888";
		String test3_str = "xxxxxx:";
		Parser container = new Parser();
		IField test1 = new FixedField("a","hello");
		
		IField test2 = new FixedField("b","1234588888888888888888888");
		
		
	//	IField test3 = new VariableField("Field3",100,':');
		container.add(test2);
	//	container.add(test3);
		container.add(test1);
		container.unpack((test2_str+test3_str+test1_str).getBytes());
		
	}*/

}
