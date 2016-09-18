package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum TestConditionType implements IEnumFieldValue<String> {
    ORDINARY("O"),
    NORMAL("N"),
    REGULAR("R"),
    IRREGULAR("I");

    private String testConditionType;

    TestConditionType(String testConditionType) {
        this.testConditionType = testConditionType;
    }

    public static TestConditionType getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (TestConditionType testConditionType : values()) {
            if (testConditionType.getValue().equals(value))
                return testConditionType;
        }

        throw new TypeNotFoundException(TestConditionType.class.getName()
                + " Error creating instance for test condition type: " + value);
    }

    @Override
    public String getValue() {
        return this.testConditionType;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
