package com.dpi.financial.ftcom.core.ndc.parser;

import com.dpi.financial.ftcom.core.ndc.parser.impl.ProCashNDC;

/**
 * Created by Hossein on 12/30/2016.
 */
public class NdcReaderFactory {
    public static NdcReader createNdcReader() {
        // return new ProCashNDC("V1.1/00");
        return new ProCashNDC("V1.3/00");
    }
}
