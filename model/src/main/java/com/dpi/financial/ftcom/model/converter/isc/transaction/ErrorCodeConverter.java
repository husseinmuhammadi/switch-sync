package com.dpi.financial.ftcom.model.converter.isc.transaction;

import com.dpi.financial.ftcom.model.type.isc.transaction.ErrorCode;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 3/11/2017.
 */
public class ErrorCodeConverter implements AttributeConverter<ErrorCode, String> {
    @Override
    public String convertToDatabaseColumn(ErrorCode errorCode) {
        if (errorCode != null)
            return errorCode.getValue();

        return null;
    }

    @Override
    public ErrorCode convertToEntityAttribute(String value) {
        return ErrorCode.getInstance(value);
    }
}
