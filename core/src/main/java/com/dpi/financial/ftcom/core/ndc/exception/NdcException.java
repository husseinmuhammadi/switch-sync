package com.dpi.financial.ftcom.core.ndc.exception;

/**
 * Created by Hossein on 12/30/2016.
 */
public class NdcException extends RuntimeException {
    public NdcException() {
        super();
    }

    public NdcException(String message) {
        super(message);
    }

    public NdcException(String message, Throwable cause) {
        super(message, cause);
    }

    public NdcException(Throwable cause) {
        super(cause);
    }

    protected NdcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
