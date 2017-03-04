package com.dpi.financial.ftcom.api.exception;

public class UnexpectedLineException extends RuntimeException {
    private static final long serialVersionUID = -132883181446131916L;

    public UnexpectedLineException() {
        super();
    }

    public UnexpectedLineException(String message) {
        super(message);
    }

    public UnexpectedLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedLineException(Throwable cause) {
        super(cause);
    }

    protected UnexpectedLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
