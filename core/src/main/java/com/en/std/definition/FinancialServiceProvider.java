package com.en.std.definition;

import com.en.std.exception.TypeNotFoundException;


public enum FinancialServiceProvider implements IEnumFieldValue<String> {
    Issuer("I"),
    Acquirer("A"),
    OnUs("O");

    private String financialServiceProvider;

    FinancialServiceProvider(String financialServiceProvider) {
        this.financialServiceProvider = financialServiceProvider;
    }

    public static FinancialServiceProvider getInstance(String sp) {
        for (FinancialServiceProvider financialServiceProvider : values()) {
            if (financialServiceProvider.getValue().equals(sp))
                return financialServiceProvider;
        }
        throw new TypeNotFoundException(FinancialServiceProvider.class.getName()
                + " Financial Service Provider Code :" + sp);
    }

    @Override
    public String getValue() {
        return this.financialServiceProvider;
    }
}
