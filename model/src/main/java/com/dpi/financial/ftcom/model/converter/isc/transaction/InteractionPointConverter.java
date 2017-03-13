package com.dpi.financial.ftcom.model.converter.isc.transaction;

import com.dpi.financial.ftcom.model.type.isc.transaction.InteractionPoint;

import javax.persistence.AttributeConverter;

/**
 * Provide an acquirer or issuer point of interaction card transaction
 * https://www.quora.com/What-is-the-difference-between-Issuer-and-Acquirer-in-debit-card-transaction
 */
public class InteractionPointConverter implements AttributeConverter<InteractionPoint, String> {
    @Override
    public String convertToDatabaseColumn(InteractionPoint interactionPoint) {
        if (interactionPoint != null)
            return interactionPoint.getValue();

        return null;
    }

    @Override
    public InteractionPoint convertToEntityAttribute(String value) {
        return InteractionPoint.getInstance(value);
    }
}
