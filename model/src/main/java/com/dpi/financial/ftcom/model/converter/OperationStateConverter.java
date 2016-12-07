package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.OperationState;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class OperationStateConverter implements AttributeConverter<OperationState, String> {
    @Override
    public String convertToDatabaseColumn(OperationState operationState) {
        if (operationState != null)
            return operationState.getValue();

        return null;
    }

    @Override
    public OperationState convertToEntityAttribute(String value) {
        return OperationState.getInstance(value);
    }
}
