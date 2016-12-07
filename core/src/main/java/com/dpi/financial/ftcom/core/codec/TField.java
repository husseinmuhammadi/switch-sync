package com.dpi.financial.ftcom.core.codec;

import com.dpi.financial.ftcom.core.hsmutil.MapList;

public class TField implements IField {
    private String name;
    private int size;
    //private String	expected;
    private byte[] value;
    private MapList valueMap;

    public TField(String name, String data) {
        this.name = name;
        this.size = data.length();
        value = data.getBytes();//new byte[this.size];
    }

    public TField(String name, int i) {
        this.name = name;
        this.size = i;
        value = new byte[this.size];

    }

    /* (non-Javadoc)
     * @see StreamParse.IField1#parse(byte[])
     */
    public int unpack(byte[] message) {
        return unpack(message, 0);
    }


    /*
       Check the first character for T field
     */
    public int unpack(byte[] message, int position) {
        char t = (char) (message[position] & 0xFF); // Z,T,U,X,Y
        //System.out.println("TField  Value:>"+t);
        switch (t) {
            case 'U':
                size = 33;
                break;
            case 'T':
                size = 49;
                break;
            case 'X':
                size = 33;
                break;
            case 'Y':
                size = 49;
                break;
            case 'Z':
                size = 16;
                break;
        }
        value = new byte[size];//dynamic size
        int i = 0;
        for (i = 0; i < size; i++, position++) {
            value[i] = message[position];
        }
        valueMap.putValue(name, new String(value));
        return position;
    }

    public String toString() {
        return " [" + size + "] :" + new String(value);
    }


    /* (non-Javadoc)
     * @see StreamParse.IField1#getValue()
     */
    public String getValue() {
        return new String(value);
    }

    public void setValue(String value) {
        this.value = value.getBytes();
    }

    public String getName() {
        return name;
    }


    /* (non-Javadoc)
     * @see StreamParse.IField#Pack()
     */
    public byte[] pack() {
        //return value;//.getBytes();// using size, need to pack
        String v = (String) valueMap.getValue(name);
        //use suitable padding
        return v.getBytes();
    }

    public void setValueMap(MapList valueMap) {
        this.valueMap = valueMap;

    }

}
