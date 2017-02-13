package com.dpi.financial.ftcom.web.controller.base.simulator.std.exception;

/**
 * @since ver 2.1.6-P7 modified by Hossein Mohammadi w.r.t Bug #12616 as on Monday, January 04, 2016
 */
public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException() {
        super();
    }

    public TypeNotFoundException(String message) {
        super(message);
    }

    public TypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause);
    }
}
