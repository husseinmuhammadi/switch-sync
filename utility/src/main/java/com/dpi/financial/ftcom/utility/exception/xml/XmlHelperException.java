package com.dpi.financial.ftcom.utility.exception.xml;

/**
 * Created by h.mohammadi on 12/11/2016.
 */
public class XmlHelperException extends RuntimeException {
    public XmlHelperException() {
        super();
    }

    public XmlHelperException(String message) {
        super(message);
    }

    public XmlHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlHelperException(Throwable cause) {
        super(cause);
    }

    protected XmlHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
