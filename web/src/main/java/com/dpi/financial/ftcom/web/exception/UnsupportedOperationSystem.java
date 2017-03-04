package com.dpi.financial.ftcom.web.exception;

/**
 * Created by h.mohammadi on 3/1/2017.
 */
public class UnsupportedOperationSystem extends RuntimeException {
    private static final long serialVersionUID = -4144798417474808981L;

    public UnsupportedOperationSystem() {
        super();
    }

    public UnsupportedOperationSystem(String message) {
        super(message);
    }

    public UnsupportedOperationSystem(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedOperationSystem(Throwable cause) {
        super(cause);
    }

    protected UnsupportedOperationSystem(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
