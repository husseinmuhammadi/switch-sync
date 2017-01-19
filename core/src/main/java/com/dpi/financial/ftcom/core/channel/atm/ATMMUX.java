package com.dpi.financial.ftcom.core.channel.atm;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.q2.iso.QMUX;

/**
 * Created by h.mohammadi on 12/25/2016.
 */
public class ATMMUX extends QMUX {
    @Override
    public String getKey(ISOMsg m) throws ISOException {
        return super.getKey(m);
    }
}
