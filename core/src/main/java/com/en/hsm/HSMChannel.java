package com.en.hsm;
import java.io.IOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOException;
import org.jpos.util.Logger;
import org.jpos.util.LogEvent;
import org.jpos.iso.channel.NACChannel;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;

public class HSMChannel extends NACChannel {
    String schema;

    public ISOMsg createMsg() {
        return new FSDISOMsg (new FSDMsg (schema));
    }
    public void setConfiguration (Configuration cfg)
        throws ConfigurationException
    {
        super.setConfiguration (cfg);
      //  super.setHeader(new byte[42]);//check header
        schema = cfg.get ("schema");
        
    }
    protected int getMessageLength() throws IOException, ISOException {
        int len = super.getMessageLength();
        LogEvent evt = new LogEvent (this, "fsd-channel-debug");
        evt.addMessage ("received message length: " + len);
        Logger.log (evt);
        return len;
    }
} 