package com.thales.products.hsm;

import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.NACChannel;

import java.io.IOException;

/**
 * Created by h.mohammadi on 11/8/2016.
 */
public class HSMChannel extends NACChannel {


    public ISOMsg createMsg() {
        return new FSDISOMsg (new RacalParser().racal());
    }
    public void setConfiguration (Configuration cfg)
            throws ConfigurationException
    {
        super.setConfiguration (cfg);
        //  super.setHeader(new byte[42]);//check header
    }
    protected int getMessageLength() throws IOException, ISOException {
        int len = super.getMessageLength();
        // LogEvent evt = new LogEvent (this, "fsd-channel-debug");
        // evt.addMessage ("received message length: " + len);
        //Logger.log (evt);
        return len;
    }
}