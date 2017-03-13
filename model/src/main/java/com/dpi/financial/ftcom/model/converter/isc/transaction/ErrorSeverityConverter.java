package com.dpi.financial.ftcom.model.converter.isc.transaction;

import com.dpi.financial.ftcom.model.type.isc.transaction.ErrorSeverity;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 3/11/2017.
 */
public class ErrorSeverityConverter implements AttributeConverter<ErrorSeverity, String> {
    @Override
    public String convertToDatabaseColumn(ErrorSeverity errorSeverity) {
        if (errorSeverity != null)
            return errorSeverity.getValue();

        return null;
    }

    @Override
    public ErrorSeverity convertToEntityAttribute(String value) {
        return ErrorSeverity.getInstance(value);
    }
}
