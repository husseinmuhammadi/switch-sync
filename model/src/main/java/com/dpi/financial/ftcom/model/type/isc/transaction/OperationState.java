package com.dpi.financial.ftcom.model.type.isc.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 3/11/2017.
 */
public enum OperationState implements IEnumFieldValue<String> {
    SUCCESSFUL("SF"),
    PARTIAL_COMPLETED("PC"),
    NOT_COMPLETE("NC"),
    NOT_DONE("ND"),
    CANCELED("CL"),
    NULL("NU");

    private final String operationState;

    OperationState(String operationState) {
        this.operationState = operationState;
    }

    public static OperationState getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (OperationState operationState : values()) {
            if (operationState.getValue().equals(value))
                return operationState;
        }

        throw new TypeNotFoundException(OperationState.class.getName()
                + " Error creating instance for OperationState: " + value);
    }

    @Override
    public String getValue() {
        return this.operationState;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
