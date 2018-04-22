package com.dpi.financial.ftcom.service.exception;

public class InvalidSynchronizeException extends RuntimeException {
    public InvalidSynchronizeException() {
        super();
    }

    public InvalidSynchronizeException(String message) {
        super(message);
    }

    public InvalidSynchronizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSynchronizeException(Throwable cause) {
        super(cause);
    }

    protected InvalidSynchronizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
