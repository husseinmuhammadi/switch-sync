package com.dpi.financial.ftcom.service.exception.atm.journal;

public class StateProcessNotDefinedException extends RuntimeException {

    public StateProcessNotDefinedException() {
        super();
    }

    public StateProcessNotDefinedException(String message) {
        super(message);
    }

    public StateProcessNotDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateProcessNotDefinedException(Throwable cause) {
        super(cause);
    }

    protected StateProcessNotDefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
