package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.atm.journal.JournalFileState;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class TerminalTransactionStateConverter implements AttributeConverter<TerminalTransactionState, String> {
    @Override
    public String convertToDatabaseColumn(TerminalTransactionState terminalTransactionState) {
        if (terminalTransactionState != null)
            return terminalTransactionState.getValue();

        return null;
    }

    @Override
    public TerminalTransactionState convertToEntityAttribute(String value) {
        return TerminalTransactionState.getInstance(value);
    }
}
