package com.dpi.financial.ftcom.core.ndc.parser.impl;

import com.dpi.financial.ftcom.core.ndc.exception.NdcException;
import com.dpi.financial.ftcom.core.ndc.parser.NdcReader;

import javax.validation.constraints.NotNull;

/**
 * Created by Hossein on 12/30/2016.
 */
public class ProCashNDC implements NdcReader {
    private final String version;

    public ProCashNDC(@NotNull String version) {
        if (!version.equals("V1.3/00"))
            throw new NdcException("Invalid ProCashNDC Version");
        this.version = version;
    }
}
