package com.dpi.financial.ftcom.model.type.atm.ndc;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * This one-character data field defines the type of message being transmitted.
 * The only valid value for the Transaction Request message is 1.
 */
public enum ErrorSeverity implements IEnumFieldValue<Byte> {
    NO_ERROR((byte)0),
    ROUTINE_ERROR((byte)1),
    WARNING((byte)2),
    SUSPEND((byte)3),
    FATAL((byte)4),
    ;

    private Byte errorSeverity;

    ErrorSeverity(Byte errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public static ErrorSeverity getInstance(Byte value) {
        if (value == null /*|| value.isDefault()*/)
            return null;

        for (ErrorSeverity errorSeverity : values()) {
            if (errorSeverity.getValue().equals(value))
                return errorSeverity;
        }

        throw new TypeNotFoundException(ErrorSeverity.class.getName()
                + " Error creating instance for ErrorSeverity: " + value);
    }

    @Override
    public Byte getValue() {
        return this.errorSeverity;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
