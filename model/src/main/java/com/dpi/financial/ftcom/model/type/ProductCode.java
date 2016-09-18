package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum ProductCode implements IEnumFieldValue<String> {
    DEBIT_CARD("D"),
    BON_CARD("B"),
    VIRTUAL_CARD("V"),
    GIFT_CARD("G"),
    ADMIN_CARD("A"),
    CREDIT_CARD("C"),
    ONLINE_PREPAID("O"),
    RESERVED("R");

    private String productCode;

    ProductCode(String productCode) {
        this.productCode = productCode;
    }

    public static ProductCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (ProductCode productCode : values()) {
            if (productCode.getValue().equals(value))
                return productCode;
        }

        throw new TypeNotFoundException(ProductCode.class.getName()
                + " Error creating instance for product code : " + value);
    }

    @Override
    public String getValue() {
        return this.productCode;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
