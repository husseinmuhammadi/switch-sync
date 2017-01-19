package com.dpi.financial.ftcom.core.ndc.parser.impl;

import com.dpi.financial.ftcom.core.ndc.exception.NdcException;
import com.dpi.financial.ftcom.core.ndc.parser.NdcReader;

import javax.validation.constraints.NotNull;

/**
 * Created by Hossein on 12/30/2016.
 */
public class ProConsultNDC implements NdcReader {
    private final String version;

    public ProConsultNDC(@NotNull String version) {
        if (!version.equals("V1.1/00"))
            throw new NdcException("Invalid ProConsultNDC Version");

        this.version = version;
    }
}
