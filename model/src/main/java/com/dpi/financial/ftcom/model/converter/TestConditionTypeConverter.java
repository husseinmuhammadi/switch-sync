package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.TestConditionType;
import com.dpi.financial.ftcom.model.type.TransactionMode;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class TestConditionTypeConverter implements AttributeConverter<TestConditionType, String> {
    @Override
    public String convertToDatabaseColumn(TestConditionType testConditionType) {
        if (testConditionType != null)
            return testConditionType.getValue();

        return null;
    }

    @Override
    public TestConditionType convertToEntityAttribute(String value) {
        return TestConditionType.getInstance(value);
    }
}
