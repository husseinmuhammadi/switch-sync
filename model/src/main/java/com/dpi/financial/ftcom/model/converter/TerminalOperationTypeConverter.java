package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.terminal.TerminalOperationType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class TerminalOperationTypeConverter implements AttributeConverter<TerminalOperationType, String> {
    @Override
    public String convertToDatabaseColumn(TerminalOperationType terminalOperationType) {
        if (terminalOperationType != null)
            return terminalOperationType.getValue();

        return null;
    }

    @Override
    public TerminalOperationType convertToEntityAttribute(String value) {
        return TerminalOperationType.getInstance(value);
    }
}
