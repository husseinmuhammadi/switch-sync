package com.dpi.financial.ftcom.core.atm.iso.ndc.msg;

import com.dpi.financial.ftcom.core.atm.iso.ndc.packager.NdcPackager;
import com.dpi.financial.ftcom.model.type.atm.ndc.MessageClass;
import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;
import org.jpos.iso.ISOMsg;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Hossein on 1/4/2017.
 */
public class NdcMsg extends ISOMsg {
    // protected NdcPackager ndcPackager;

    private MessageClass messageClass;
    private MessageIdentifier messageIdentifier;

    public NdcMsg() {
    }

    public NdcMsg(int fieldNumber) {
        super(fieldNumber);
    }

    public NdcMsg(String mti) {
        super(mti);
    }

    public NdcMsg(ISOMsg msg) {
        this();
    }

    public MessageClass getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(MessageClass messageClass) {
        this.messageClass = messageClass;
    }

    public MessageIdentifier getMessageIdentifier() {
        return messageIdentifier;
    }

    public void setMessageIdentifier(MessageIdentifier messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }
}
