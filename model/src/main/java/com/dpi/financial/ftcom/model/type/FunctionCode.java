package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum FunctionCode implements IEnumFieldValue<String> {

    MINI_STATEMENT("108"),
    BALANCE_ENQUIRY("108"),//ProcessCode:31
    CUSTOMER_CARD_ENQUIRY("113"),
    QIVA_ENQUIRY("113"),
    PIN_CONFIRM("180"),
    CUSTOMER_SHEBA_ENQUIRY("181"),   //ProcessCode:33
    ACQUIRER_FINANCIAL("200"),
    ACQUIRER_FINANCIAL_PURCHASE("270"),
    ACQUIRER_BILL_PAYMENT("280"),
    FUND_TRANSFER("285"), //ProcessCode:40
    FUND_TRANSFER_DR("285"), //ProcessCode:46
    FUND_TRANSFER_CR("286"); //ProcessCode:47


    private String functionCode;

    FunctionCode(String productCode) {
        this.functionCode = functionCode;
    }

    public static FunctionCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (FunctionCode productCode : values()) {
            if (productCode.getValue().equals(value))
                return productCode;
        }

        throw new TypeNotFoundException(FunctionCode.class.getName()
                + " Error creating instance for Function Code : " + value);
    }

    @Override
    public String getValue() {
        return this.functionCode;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
