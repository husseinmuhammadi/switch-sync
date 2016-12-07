package com.thales.products.hsm.core.commands.host;

import com.dpi.financial.ftcom.core.codec.Parser;

import com.thales.products.hsm.RacalParser;
import com.thales.products.hsm.ThalesAdaptor;

public class HostCommands extends ThalesAdaptor {

    public Parser generateVISACVV(String keya, String keyb, String pan, String exp, String service) throws Exception {
        // String key="C62B3BE16F8E1F61B749C78E1EFB3FEB";
        // String expDate="0909";
        // String serviceCode="000";
        // String cmd="CW"+key+pan+";"+expDate+serviceCode;
        // command("cvv gen", cmd);
        Parser req = new RacalParser().racal();
        req.setValue("command", "CW");
        req.setValue("keya", keya);
        req.setValue("keyb", keyb);
        req.setValue("pan", pan);// 12 is fixed length
        req.setValue("delim", ";");
        req.setValue("expdate", exp);
        req.setValue("servicecode", service);
        return command(req);
        //0235 CX 00 754
    }
}
