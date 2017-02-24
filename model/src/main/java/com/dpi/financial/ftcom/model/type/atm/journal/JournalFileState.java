package com.dpi.financial.ftcom.model.type.atm.journal;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

import java.text.MessageFormat;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum JournalFileState implements IEnumFieldValue<String> {
    ENTRY("E"),
    PREPARED("P"),
    PROCESSED("C"),
    RECONCILED("R"),
    ;

    private String state;

    JournalFileState(String state) {
        this.state = state;
    }

    public static JournalFileState getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (JournalFileState state : values()) {
            if (state.getValue().equals(value))
                return state;
        }

        throw new TypeNotFoundException(
                MessageFormat.format("Type not found for {0}: {1}", JournalFileState.class.getName(), value)
        );
    }

    @Override
    public String getValue() {
        return this.state;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
