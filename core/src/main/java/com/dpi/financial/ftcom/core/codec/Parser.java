package com.dpi.financial.ftcom.core.codec;

import com.dpi.financial.ftcom.core.hsmutil.MapList;

import java.io.PrintStream;
import java.util.*;


public class Parser implements IField

{
    //ArrayList required to maintain order
    public static final char FS = 0x1C;
    public static final char GS = 0x1E;
    protected ArrayList<String> fields = new ArrayList<String>();
    protected HashMap<String, IField> map = new HashMap<String, IField>();
    protected byte[] packed = null;
    protected MapList valueMap = null;

    public Parser() {
        valueMap = new MapList();
    }

    public Parser add(IField field) {
        String name = field.getName();
        fields.add(name);
        map.put(name, field);
        return this;
    }

    public void dump() {
        System.out.println(valueMap.toString());
    }

    public void dump(PrintStream ps, String indent) {
        //System.out.println(valueMap.toString());
    }

    public int unpack(byte[] message) {
        valueMap = new MapList();
        return unpack(message, 0);
    }

    /*
     * Only Top Level parser has default MapList
     * */
    public int unpack(byte[] message, int position) {

        for (Iterator<String> iter = fields.iterator(); iter.hasNext(); ) {
            {
                String name = iter.next();
                IField field = map.get(name);
                field.setValueMap(valueMap);
                position = field.unpack(message, position);
                if (position >= message.length) break;
            }
        }
        return position;
    }

    /*
     * If it is repeat field then return String array
     */
    public Object getValue(String name) {
        return valueMap.getValue(name);
    }

    /*
     * Check for null and raise exception
     */
    public void setValue(String name, String value) {
        valueMap.putValue(name, value);
    }

    /*
     * Check for Manadatory and null
     * How to detect Optional
     * Switch field should help in delegate parsers down
     */
    public byte[] pack() {
        byte[] packed = null;
        for (Iterator<String> iter = fields.iterator(); iter.hasNext(); ) {
            String name = iter.next();
            //	 	if(name==null) return packed;
            IField field = map.get(name);//iter.next();
            field.setValueMap(valueMap);//passing data using map
            byte[] b = field.pack();

            if (packed == null) {
                packed = b;
            } else {
                byte[] temp = new byte[packed.length + b.length];
                System.arraycopy(packed, 0, temp, 0, packed.length);
                System.arraycopy(b, 0, temp, packed.length, b.length);
                packed = temp;
            }
        }
        return packed;
    }

    /*  */
    public Object getValue() {
        // TODO Auto-generated method stub
        throw new RuntimeException("Should not call getValue on Parser");
        //return null;
    }

    public void setValue(String value) {
        // TODO Auto-generated method stub
        throw new RuntimeException("Should not call setValue on Parser");

    }

    public String getName() {
        throw new RuntimeException("Should not call getName on Parser");

    }

    public void setValueMap(MapList valueMap) {
        this.valueMap = valueMap;

    }
}
