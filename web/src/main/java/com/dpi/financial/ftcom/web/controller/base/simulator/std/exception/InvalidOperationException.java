package com.dpi.financial.ftcom.web.controller.base.simulator.std.exception;

/**
 * Created by h.mohammadi on 7/2/2016.
 */
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException() {
        super();
    }

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }
}
