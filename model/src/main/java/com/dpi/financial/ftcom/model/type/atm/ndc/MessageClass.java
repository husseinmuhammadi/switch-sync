package com.dpi.financial.ftcom.model.type.atm.ndc;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * This field tells the network whether the message is solicited or unsolicited. The valid entries for this field are as follows:
 * 1 - unsolicited message
 * 2 - solicited message
 * In case of a Transaction Request message, this field will be 1.
 */
public enum MessageClass implements IEnumFieldValue<String> {
    UNSOLICITED("1"),
    SOLICITED("2"),
    ;

    private String ndcMessageClass;

    MessageClass(String ndcMessageClass) {
        this.ndcMessageClass = ndcMessageClass;
    }

    public static MessageClass getInstance(String value) throws TypeNotFoundException {
        if (value == null || value.isEmpty())
            return null;

        for (MessageClass ndcMessageClass : values()) {
            if (ndcMessageClass.getValue().equals(value))
                return ndcMessageClass;
        }

        throw new TypeNotFoundException(MessageClass.class.getName()
                + " Error creating instance for MessageClass: " + value);
    }

    @Override
    public String getValue() {
        return this.ndcMessageClass;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
