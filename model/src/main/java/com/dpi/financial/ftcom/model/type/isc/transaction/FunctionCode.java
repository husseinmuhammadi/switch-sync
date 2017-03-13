package com.dpi.financial.ftcom.model.type.isc.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Error severity represent message error as mentioned in SHETAB VOL 2 TRANSACTION DETAILS VERSION 7.0.4 Page 57 Table 70
 */
// todo complete by p61 document
public enum FunctionCode implements IEnumFieldValue<String> {
    MINI_STATEMENT("108"),
    BALANCE_INQUIRY("108"),
    CUSTOMER_INQUIRY("113"),
    WALLET_INQUIRY("113"),
    PIN_VERIFICATION("180"),
    ACCOUNT_INQUIRY("181", YesNoType.Yes),
    FINANCIAL_REQUEST("200"),
    PURCHASE("270"),
    BILL_PAYMENT("280"),
    INSTALLMENT_PAYMENT("281"),
    PAYMENT_CHARGES("282"), //todo What is correct phrase?
    BILL_PAYMENT_ENHANCED("283"),
    FUND_TRANSFER_CARD_TO_ACCOUNT("285", YesNoType.Yes), // debit card & credit account
    FUND_TRANSFER_DR_FROM_CARD("286", YesNoType.Yes), // debit card
    FUND_TRANSFER_CR_TO_ACCOUNT("287", YesNoType.Yes), // credit account
    PURCHACE_BACK_COMPLETE("288"),
    PURCHACE_BACK_PARTIAL("289"),
    DEPOSIT_TO_CARD("290"),
    WITHDRAWAL_FROM_CARD("291"),
    DEPOSIT_TO_ACCOUNT("292", YesNoType.Yes),
    WITHDRAWAL_FROM_ACCOUNT("293", YesNoType.Yes),
    REVERSAL("400"), // reversal complete
    REVERSAL_PARTIAL("401"), // reversal partial
    FUND_TRANSFER_REVERSAL_CARD_TO_ACCOUNT("495", YesNoType.Yes), // debit card & credit account
    FUND_TRANSFER_DR_REVERSAL_FROM_CARD("496", YesNoType.Yes), // debit card
    FUND_TRANSFER_CR_REVERSAL_TO_ACCOUNT("497", YesNoType.Yes), // credit account
    ;

    private final String functionCode;
    private final YesNoType iban;

    FunctionCode(String functionCode, YesNoType iban) {
        this.functionCode = functionCode;
        this.iban = iban;
    }

    FunctionCode(String functionCode) {
        this(functionCode, null);
    }

    public static FunctionCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (FunctionCode functionCode : values()) {
            if (functionCode.getValue().equals(value))
                return functionCode;
        }

        throw new TypeNotFoundException(FunctionCode.class.getName()
                + " Error creating instance for FunctionCode: " + value);
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
