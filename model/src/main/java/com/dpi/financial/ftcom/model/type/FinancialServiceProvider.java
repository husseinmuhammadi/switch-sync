package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum FinancialServiceProvider implements IEnumFieldValue<String> {
    Issuer("I"),
    Acquirer("A"),
    OnUs("O");

    private String financialServiceProvider;

    FinancialServiceProvider(String financialServiceProvider) {
        this.financialServiceProvider = financialServiceProvider;
    }

    public static FinancialServiceProvider getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (FinancialServiceProvider financialServiceProvider : values()) {
            if (financialServiceProvider.getValue().equals(value))
                return financialServiceProvider;
        }

        throw new TypeNotFoundException(FinancialServiceProvider.class.getName()
                + " Error creating instance for financial service provider: " + value);
    }

    @Override
    public String getValue() {
        return this.financialServiceProvider;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}