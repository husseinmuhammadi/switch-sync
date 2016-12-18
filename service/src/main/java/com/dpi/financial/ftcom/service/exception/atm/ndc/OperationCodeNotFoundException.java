package com.dpi.financial.ftcom.service.exception.atm.ndc;

public class OperationCodeNotFoundException extends RuntimeException {
    public OperationCodeNotFoundException() {
        super();
    }

    public OperationCodeNotFoundException(String message) {
        super(message);
    }

    public OperationCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationCodeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected OperationCodeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
