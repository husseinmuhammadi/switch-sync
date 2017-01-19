package com.dpi.financial.ftcom.core.packager.ndc;

import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;
import com.dpi.financial.ftcom.core.ndc.parser.NdcReader;
import com.dpi.financial.ftcom.core.ndc.parser.NdcReaderFactory;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.util.LogEvent;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

import java.io.*;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

/**
 * Created by h.mohammadi on 1/3/2017.
 */
public abstract class BasePackager implements LogSource, ISOPackager {

    private NdcReader reader = null;
    private Stack stk;

    public BasePackager() throws ISOException {
        super();

        stk = new Stack();
        try {
            reader = createNdcReader();
        } catch (Exception e) {
            throw new ISOException(e.toString());
        }
    }

    private NdcReader createNdcReader() {
        NdcReader reader;
        reader = NdcReaderFactory.createNdcReader();
        return reader;
    }

    protected abstract NdcMsg getNdcMsg();

    protected abstract List<Function<ByteArrayInputStream, BasePackager>> getSupplierList();

    protected void unpack(ByteArrayInputStream in) {
        getSupplierList().forEach(f -> f.apply(in));
    }





    @Override
    public byte[] pack(ISOComponent component) throws ISOException {
        byte[] b = new byte[0];
        //            if (!(component instanceof NdcMsg)) {
//                throw new ISOException("cannot pack " + component.getClass());
//            }
//
//            NdcMsg msg = (NdcMsg) component;

        synchronized (this) {
            // to pack here
        }

        return b;
    }

    public int unpack(ISOComponent component, byte[] b) throws ISOException {
        LogEvent evt = new LogEvent(this, "unpack");
        try {
            // DataInputStream in = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(b)));
            // BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.US_ASCII));

            unpack(new ByteArrayInputStream(b));
        } finally {
            Logger.log(evt);
        }

        return b.length;
    }

    @Override
    public String getDescription() {
        return getClass().getName();
    }

    @Override
    public String getFieldDescription(ISOComponent m, int fldNumber) {
        return null;
    }


}
