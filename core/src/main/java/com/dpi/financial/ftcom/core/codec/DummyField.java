package com.dpi.financial.ftcom.core.codec;


import com.dpi.financial.ftcom.core.hsmutil.MapList;

public class DummyField implements IField {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public byte[] pack() {
        return new byte[0];
    }

    @Override
    public void setValueMap(MapList valueMap) {

    }

    @Override
    public int unpack(byte[] message) {
        return 0;
    }

    @Override
    public int unpack(byte[] message, int position) {
        return 0;
    }

}
