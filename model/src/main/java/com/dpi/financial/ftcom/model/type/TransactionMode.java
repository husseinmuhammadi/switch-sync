package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum TransactionMode implements IEnumFieldValue<String> {
    ONLINE("N"),
    OFFLINE("Y"),
    REJETC("R");

    private String transactionMode;

    TransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public static TransactionMode getInstance(String value) {
        for (TransactionMode transactionMode : values()) {
            if (transactionMode.getValue().equals(value))
                return transactionMode;
        }
        throw new TypeNotFoundException(TransactionMode.class.getName()
                + " Error creating instance for transaction mode : " + value);
    }

    @Override
    public String getValue() {
        return this.transactionMode;
    }
}
