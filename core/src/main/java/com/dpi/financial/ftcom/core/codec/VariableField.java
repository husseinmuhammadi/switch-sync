package com.dpi.financial.ftcom.core.codec;

import com.dpi.financial.ftcom.core.hsmutil.ArrayUtil;
import com.dpi.financial.ftcom.core.hsmutil.MapList;

public class VariableField implements IField {

    private char field_terminator;
    private int size;
    private byte[] value;
    private String name;
    private MapList valueMap;

    public VariableField(String name, int max_length, char field_terminator) {
        this.name = name;
        this.field_terminator = field_terminator;
        this.size = max_length;

    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return new String(value);
    }

    public void setValue(String value) {
        this.value = value.getBytes();
    }

    public int unpack(byte[] message) {
        return unpack(message, 0);
    }

    public int unpack(byte[] message, int position) {

        String temp = new String(message);
        int i = temp.indexOf(field_terminator, position);

        if (i == -1) //not found
        {
            if (message.length - position <= size) {
                value = temp.substring(position, message.length).getBytes();
                return message.length + 1;
            }
        } else
            value = temp.substring(position, i).getBytes();
        valueMap.putValue(name, new String(value));
        return i + 1;
    }

    public String toString() {

        {
            return " [Var " + size + " Sep" + field_terminator + "]:" + new String(value);
        }
    }


    public byte[] pack() {
        //return ((String)map.get(name)+ field_terminator).getBytes();
        return ArrayUtil.merge(value, new byte[]{(byte) field_terminator});
    }

    public void setValueMap(MapList valueMap) {
        this.valueMap = valueMap;

    }
}
