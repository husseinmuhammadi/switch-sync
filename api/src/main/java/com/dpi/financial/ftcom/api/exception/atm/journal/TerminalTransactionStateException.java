package com.dpi.financial.ftcom.api.exception.atm.journal;

public class TerminalTransactionStateException extends RuntimeException {
    public TerminalTransactionStateException() {
        super();
    }

    public TerminalTransactionStateException(String message) {
        super(message);
    }

    public TerminalTransactionStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerminalTransactionStateException(Throwable cause) {
        super(cause);
    }

    protected TerminalTransactionStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
