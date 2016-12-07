package com.dpi.financial.ftcom.service.exception;

/**
 * Created by h.mohammadi on 12/3/2016.
 */
public class JournalFilesNotExists extends RuntimeException {
    public JournalFilesNotExists() {
        super();
    }

    public JournalFilesNotExists(String message) {
        super(message);
    }

    public JournalFilesNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public JournalFilesNotExists(Throwable cause) {
        super(cause);
    }

    protected JournalFilesNotExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
