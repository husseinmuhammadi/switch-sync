package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.OperationState;
import com.dpi.financial.ftcom.model.type.atm.journal.JournalFileState;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class JournalFileStateConverter implements AttributeConverter<JournalFileState, String> {
    @Override
    public String convertToDatabaseColumn(JournalFileState journalFileState) {
        if (journalFileState != null)
            return journalFileState.getValue();

        return null;
    }

    @Override
    public JournalFileState convertToEntityAttribute(String value) {
        return JournalFileState.getInstance(value);
    }
}
