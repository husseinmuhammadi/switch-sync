package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

public enum OperationState implements IEnumFieldValue<String> {
    START("S"),
    ERROR("E"),
    RUNNING("R"),
    IN_PROGRESS("P"), //PREPARE, PENDING
    TERMINATED("T"),
    FINISHED("F")
    ;

    private String operationState;

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
                + " Error creating instance for operation state : " + value);
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
