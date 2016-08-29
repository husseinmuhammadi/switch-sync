package com.dpi.financial.ftcom.utility.exception.model;

public class EntityIdIsNullException extends RuntimeException {
    public EntityIdIsNullException() {
    }

    public EntityIdIsNullException(String message) {
        super(message);
    }

    public EntityIdIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityIdIsNullException(Throwable cause) {
        super(cause);
    }

    protected EntityIdIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
