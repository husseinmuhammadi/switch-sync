package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.TransactionMode;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class TransactionModeConverter implements AttributeConverter<TransactionMode, String> {
    @Override
    public String convertToDatabaseColumn(TransactionMode transactionMode) {
        if (transactionMode != null)
            return transactionMode.getValue();

        return null;
    }

    @Override
    public TransactionMode convertToEntityAttribute(String value) {
        return TransactionMode.getInstance(value);
    }
}
