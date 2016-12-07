package com.dpi.financial.ftcom.service.exception;

public class OperationTerminatedException extends RuntimeException {
    public OperationTerminatedException() {
        super();
    }

    public OperationTerminatedException(String message) {
        super(message);
    }

    public OperationTerminatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationTerminatedException(Throwable cause) {
        super(cause);
    }

    protected OperationTerminatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
