package com.dpi.financial.ftcom.utility.exception.model;

public class EntityNotExists extends RuntimeException {
    public EntityNotExists() {
        super();
    }

    public EntityNotExists(String message) {
        super(message);
    }

    public EntityNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotExists(Throwable cause) {
        super(cause);
    }

    protected EntityNotExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
