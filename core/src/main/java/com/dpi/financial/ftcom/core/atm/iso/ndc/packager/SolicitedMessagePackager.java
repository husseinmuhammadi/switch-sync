package com.dpi.financial.ftcom.core.atm.iso.ndc.packager;

import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;
import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.SolicitedMessage;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by h.mohammadi on 1/3/2017.
 */
public class SolicitedMessagePackager extends NdcPackager {
    // private final SolicitedMessage solicitedMessage;

    public SolicitedMessagePackager() throws ISOException {
    }

    @Override
    public byte[] pack() {
        return new byte[0];
    }

    @Override
    public NdcMsg unpack(InputStream in) throws IOException {
        return null;
    }

    @Override
    protected NdcMsg createNdcMsg() {
        return new SolicitedMessage();
    }

    /*
    @Override
    public SolicitedMessage getNdcMsg() {
        if (!(ndcMsg instanceof SolicitedMessage))
            return null;

        return (SolicitedMessage) ndcMsg;
    }
    */

    public SolicitedMessagePackager(NdcMsg ndcMsg) throws ISOException {
        super();
        //solicitedMessage = new SolicitedMessage(ndcMsg);
    }

    @Override
    public byte[] pack(ISOComponent m) throws ISOException {
        return new byte[0];
    }

    @Override
    public int unpack(ISOComponent m, byte[] b) throws ISOException {
        return 0;
    }

    @Override
    public void unpack(ISOComponent m, InputStream in) throws IOException, ISOException {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getFieldDescription(ISOComponent m, int fldNumber) {
        return null;
    }

    @Override
    public ISOMsg createISOMsg() {
        return null;
    }
}
