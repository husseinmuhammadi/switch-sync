package com.dpi.financial.ftcom.core.atm.iso.ndc.packager;

import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;
import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.UnsolicitedMessage;
import com.dpi.financial.ftcom.model.type.ascii.ControlCharacter;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOMsg;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by h.mohammadi on 1/3/2017.
 */
public class UnsolicitedMessagePackager extends NdcPackager {
    public UnsolicitedMessagePackager() {
        super();
    }

    @Override
    public byte[] pack() {
        // packLogicalUnitNumber();
        // packFS();
        // packStatusInformation();

        return new byte[0];
    }

    @Override
    protected NdcMsg createNdcMsg() {
        return new UnsolicitedMessage();
    }

    /*
    @Override
    public UnsolicitedMessage getNdcMsg() {
        if (!(ndcMsg instanceof UnsolicitedMessage))
            return null;

        return (UnsolicitedMessage) ndcMsg;
    }
    */

    @Override
    public NdcMsg unpack(InputStream in) throws IOException {
        unpackLogicalUnitNumber(in);
        unpackFS(in);
        unpackStatusInformation(in);
        return null;
    }

    protected void unpackLogicalUnitNumber(InputStream in) {
        // logical unit number (LUNO)
        String luno = readSymbol(in, ControlCharacter.FS);
        System.out.println(luno);
        // getNdcMsg().setLuno(luno);
    }

    protected void unpackStatusInformation(InputStream in) {
        // status information
        String statusInformation = readSymbol(in, ControlCharacter.ETX);
        System.out.println(statusInformation);
        // getNdcMsg().setStatusInformation(statusInformation);
    }
}
