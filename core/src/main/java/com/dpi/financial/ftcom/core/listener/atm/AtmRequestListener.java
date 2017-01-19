package com.dpi.financial.ftcom.core.listener.atm;

import com.dpi.financial.ftcom.core.atm.iso.ndc.msg.NdcMsg;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.*;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.NameRegistrar;

import java.io.IOException;

/**
 * Created by Hossein on 12/20/2016.
 */
public class AtmRequestListener implements ISORequestListener, LogSource, Configurable {
    private Logger logger;
    private String realm;
    private Configuration cfg;

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        this.cfg = cfg;
    }

    @Override
    public boolean process(ISOSource source, ISOMsg msg) {
        if (!(msg instanceof NdcMsg))
            return false;
        NdcMsg ndcMsg = (NdcMsg) msg;

        long timeout = 40000;

        try {
            MUX mux = QMUX.getMUX("switch-ndc-mux");

            if (mux.isConnected()) {
                System.out.println("Mux is connected!");
                System.out.println("Sending response to switch.");
                /*
                ISOMsg response = mux.request(container, timeout);
                if (response == null) {
                    System.out.println("switch timeout");
                    return false;
                }
                */
                mux.send(ndcMsg);
            }
        } catch (ISOException | NameRegistrar.NotFoundException | IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void setLogger(Logger logger, String realm) {
        this.logger = logger;
        this.realm = realm;
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
