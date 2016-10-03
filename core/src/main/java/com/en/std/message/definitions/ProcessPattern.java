package com.en.std.message.definitions;

import com.en.std.exception.TypeNotFoundException;


public enum ProcessPattern implements EntityFieldType {
    Issuer("I"),
    Acquirer("A"),
    OnUs("O");

    private String processPatternValue;

    ProcessPattern(String processPatternValue) {
        this.processPatternValue = processPatternValue;
    }

    public static ProcessPattern getInstance(String processPatternValue) {
        if (processPatternValue == null) {
            return null;
        }
        
        for (ProcessPattern processPattern : values()) {
            if (processPattern.getValue().equals(processPatternValue)) {
                return processPattern;
            }
        }
        
        throw new TypeNotFoundException(ProcessPattern.class.getName() + " Process Pattern Value:" + processPatternValue);
    }

    @Override
    public String getValue() {
        return this.processPatternValue;
    }
}