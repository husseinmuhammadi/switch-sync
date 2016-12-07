package com.dpi.financial.ftcom.security.design.command;

import com.dpi.financial.ftcom.security.api.module.SecurityModule;

public class Broker {
    public Broker(SecurityModule module) {
    }

    public void process(ICommand command){
        command.execute();
    }
}
