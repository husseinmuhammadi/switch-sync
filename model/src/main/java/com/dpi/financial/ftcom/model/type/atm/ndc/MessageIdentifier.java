package com.dpi.financial.ftcom.model.type.atm.ndc;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * This one-character data field defines the type of message being transmitted.
 * The only valid value for the Transaction Request message is 1.
 */
public enum MessageIdentifier implements IEnumFieldValue<String> {
    TRANSACTION_REQUEST("11"),
    UNSOLICITED_STATUS("12"),
    SOLICITED_STATUS("22"),
    ;

    private String ndcMessageIdentifier;

    MessageIdentifier(String ndcMessageIdentifier) {
        this.ndcMessageIdentifier = ndcMessageIdentifier;
    }

    public static MessageIdentifier getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (MessageIdentifier ndcMessageIdentifier : values()) {
            if (ndcMessageIdentifier.getValue().equals(value))
                return ndcMessageIdentifier;
        }

        throw new TypeNotFoundException(MessageIdentifier.class.getName()
                + " Error creating instance for MessageIdentifier: " + value);
    }

    @Override
    public String getValue() {
        return this.ndcMessageIdentifier;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
