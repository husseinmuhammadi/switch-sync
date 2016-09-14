package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.FinancialServiceProvider;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class FinancialServiceProviderConverter implements AttributeConverter<FinancialServiceProvider, String> {
    @Override
    public String convertToDatabaseColumn(FinancialServiceProvider financialServiceProvider) {
        if (financialServiceProvider != null)
            return financialServiceProvider.getValue();

        return null;
    }

    @Override
    public FinancialServiceProvider convertToEntityAttribute(String value) {
        return FinancialServiceProvider.getInstance(value);
    }
}
