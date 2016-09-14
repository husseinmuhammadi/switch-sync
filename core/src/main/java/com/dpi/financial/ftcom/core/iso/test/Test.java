package com.dpi.financial.ftcom.core.iso.test;

import com.dpi.financial.ftcom.core.iso.packager.ShetabISO87APackager;
import com.dpi.financial.ftcom.model.to.Product;
import com.dpi.financial.ftcom.model.type.ProductCode;
import org.jpos.iso.*;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.channel.XMLChannel;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.iso.packager.XMLPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import javax.websocket.Encoder;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        try {
            // PackAndUnpack();
            // ISOChannelExample();
            ISOServerExample();
        } catch (ISOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * In real life applications, you want to use Q2’s QServer component instead.
     * @throws ISOException
     * @throws IOException
     */
    private static void ISOServerExample() throws ISOException, IOException {
        Logger logger = new Logger ();
        logger.addListener (new SimpleLogListener (System.out));
        ServerChannel channel = new XMLChannel(new XMLPackager());
        ((LogSource)channel).setLogger (logger, "channel");
        ISOServer server = new ISOServer (8000, channel, null);
        server.setLogger (logger, "server");
        new Thread (server).start ();
    }

    private static void ISOChannelExample() throws IOException, ISOException {
        Logger logger = new Logger();
        logger.addListener(new SimpleLogListener(System.out));
        ISOChannel channel = new ASCIIChannel(
                "localhost", 7, new ISO87APackager()
        );
        ((LogSource) channel).setLogger(logger, "test-channel");
        channel.connect();
        ISOMsg m = new ISOMsg();
        m.setMTI("0800");
        m.set(3, "000000");
        m.set(41, "00000001");
        m.set(70, "301");
        channel.send(m);
        ISOMsg r = channel.receive();
        channel.disconnect();
    }

    private static void PackAndUnpack() throws ISOException {
        ISOPackager packager = new ShetabISO87APackager();
        ISOMsg msg = new ISOMsg();
        msg.setPackager(packager);

        msg.setMTI("0800");
        msg.set(3, "000000");
        msg.set(11, "000001");
        msg.set(41, "29110001");
        msg.set(60, "jPOS 6");
        msg.set(70, "301");

        byte[] binaryImage = msg.pack();

        ISOMsg msg1 = new ISOMsg();
        msg1.setPackager(packager);

        msg1.unpack(binaryImage);

        System.out.println("");
    }
}
