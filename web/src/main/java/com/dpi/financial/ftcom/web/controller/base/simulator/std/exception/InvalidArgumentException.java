package com.dpi.financial.ftcom.web.controller.base.simulator.std.exception;

/**
 * @since ver 2.1.6-P7 modified by Hossein Mohammadi w.r.t Bug #12616 as on Monday, January 04, 2016
 */
public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }
}
