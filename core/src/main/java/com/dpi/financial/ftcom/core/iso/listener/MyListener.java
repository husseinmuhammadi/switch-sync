package com.dpi.financial.ftcom.core.iso.listener;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

/**
 * Created by h.mohammadi on 9/5/2016.
 */
public class MyListener implements ISORequestListener {
    @Override
    public boolean process(ISOSource source, ISOMsg m) {
        return false;
    }
}
