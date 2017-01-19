package com.dpi.financial.ftcom.core.atm.iso.ndc.packager;

import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Hossein on 1/6/2017.
 */
public class NdcBasePackager extends NdcPackager {
    @Override
    protected NdcMsg createNdcMsg() {
        return new NdcMsg();
    }

    @Override
    public byte[] pack() {
        return new byte[0];
    }

    @Override
    public NdcMsg unpack(InputStream in) throws IOException {
        return null;
    }


}
