package com.dpi.financial.ftcom.model.type.isc.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Error severity represent message error as mentioned in SHETAB VOL 2 TRANSACTION DETAILS VERSION 7.0.4 Page 57 Table 70
 */
public enum ErrorSeverity implements IEnumFieldValue<String> {
    ERROR("00"),
    WARNING("01"),;

    private String errorSeverity;

    ErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public static ErrorSeverity getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (ErrorSeverity errorSeverity : values()) {
            if (errorSeverity.getValue().equals(value))
                return errorSeverity;
        }

        throw new TypeNotFoundException(ErrorSeverity.class.getName()
                + " Error creating instance for Error Severity: " + value);
    }

    @Override
    public String getValue() {
        return this.errorSeverity;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
