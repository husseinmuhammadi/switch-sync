package com.dpi.financial.ftcom.utility.exception;

/**
 * Created by h.mohammadi on 12/6/2016.
 */
public class MultipleMatchException extends Exception {
    public MultipleMatchException() {
        super();
    }

    public MultipleMatchException(String message) {
        super(message);
    }

    public MultipleMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleMatchException(Throwable cause) {
        super(cause);
    }

    protected MultipleMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
