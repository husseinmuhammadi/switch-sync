package com.dpi.financial.ftcom.core.listener.atm;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;

/**
 * Created by h.mohammadi on 12/31/2016.
 */
public class SwitchRequestListener implements ISORequestListener, LogSource, Configurable {
    private Logger logger;
    private String realm;

    @Override
    public void setConfiguration(Configuration cfg) throws ConfigurationException {

    }

    @Override
    public boolean process(ISOSource source, ISOMsg m) {
        return false;
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
