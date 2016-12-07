package com.dpi.financial.ftcom.security.design.command.pin.generation;

import com.dpi.financial.ftcom.security.design.command.ICommand;

/**
 * Created by h.mohammadi on 11/9/2016.
 */
public class GenerateIBMPINOffsetCommand implements ICommand {
    private PINAndOffsetGeneration pinAndOffsetGeneration;

    public GenerateIBMPINOffsetCommand(PINAndOffsetGeneration pinAndOffsetGeneration) {
        this.pinAndOffsetGeneration = pinAndOffsetGeneration;
    }

    @Override
    public void execute() {
        pinAndOffsetGeneration.generateIBMPINOffset();
    }
}

