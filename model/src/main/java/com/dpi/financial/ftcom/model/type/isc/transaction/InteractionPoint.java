package com.dpi.financial.ftcom.model.type.isc.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Provide an acquirer or issuer point of interaction card transaction
 * https://www.quora.com/What-is-the-difference-between-Issuer-and-Acquirer-in-debit-card-transaction
 */
public enum InteractionPoint implements IEnumFieldValue<String> {
    Issuer("I"),
    Acquirer("A"),
    OnUs("O");

    private String interactionPoint;

    InteractionPoint(String interactionPoint) {
        this.interactionPoint = interactionPoint;
    }

    public static InteractionPoint getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (InteractionPoint interactionPoint : values()) {
            if (interactionPoint.getValue().equals(value))
                return interactionPoint;
        }

        throw new TypeNotFoundException(InteractionPoint.class.getName()
                + " Error creating instance for Interaction Point: " + value);
    }

    @Override
    public String getValue() {
        return this.interactionPoint;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}