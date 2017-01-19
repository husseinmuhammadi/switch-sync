package com.dpi.financial.ftcom.core.channel.atm;

import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * Created by Hossein on 12/20/2016.
 */
public class AtmChannelTemp extends BaseChannel {
    String schema;
    BufferedReader reader = null;

    public AtmChannelTemp() {
        super();
    }

    public AtmChannelTemp(String host, int port, ISOPackager p) {
        super(host, port, p);
    }

    public AtmChannelTemp(ISOPackager p) throws IOException {
        super(p);
    }

    public AtmChannelTemp(ISOPackager p, ServerSocket serverSocket) throws IOException {
        super(p, serverSocket);
    }

    @Override
    protected ISOMsg createMsg() {
        return super.createMsg();
        // return new AtmNdc(new AtmPackager(schema));
    }

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        super.setConfiguration(cfg);
        schema = cfg.get("schema");
    }

    @Override
    protected int getMessageLength() throws IOException, ISOException {
        int len = super.getMessageLength();
        LogEvent evt = new LogEvent(this, "fsd-channel-debug");
        evt.addMessage("received message length: " + len);
        Logger.log(evt);
        return len;
    }

    @Override
    protected byte[] streamReceive() throws IOException {
        // return super.streamReceive();
        System.out.println("com.dpi.financial.ftcom.core.channel.atm.AtmChannel.streamReceive");
        int i = 0;
        byte[] buf = new byte[4096];
        for (i = 0; i < 4096; i++) {
            int c = -1;
            try {
                c = serverIn.read();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (c == 03) {
                break;
            } else if (c == -1) {
                throw new EOFException("connection closed");
            }
            buf[i] = (byte) c;
            System.out.println("read atm ndc buffer: " + i);
        }
        if (i == 4096) {
            throw new IOException("packet too long");
        }

        byte[] d = new byte[i];
        System.arraycopy(buf, 0, d, 0, i);
        return d;
    }
}