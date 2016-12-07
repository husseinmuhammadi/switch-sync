package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

public enum ProcessState implements IEnumFieldValue<String> {
    Created("C"),
    Ready("R"),
    Running("N"),
    Blocked("B"),
    Terminated("T"),;

    private String processState;

    ProcessState(String processState) {
        this.processState = processState;
    }

    public static ProcessState getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (ProcessState processState : values()) {
            if (processState.getValue().equals(value))
                return processState;
        }

        throw new TypeNotFoundException(ProcessState.class.getName()
                + " Error creating instance for process state : " + value);
    }

    @Override
    public String getValue() {
        return this.processState;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
