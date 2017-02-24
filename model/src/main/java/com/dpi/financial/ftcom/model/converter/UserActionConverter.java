package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.terminal.transaction.UserAction;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class UserActionConverter implements AttributeConverter<UserAction, String> {
    @Override
    public String convertToDatabaseColumn(UserAction action) {
        if (action != null)
            return action.getValue();

        return null;
    }

    @Override
    public UserAction convertToEntityAttribute(String value) {
        return UserAction.getInstance(value);
    }
}
