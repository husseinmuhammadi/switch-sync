package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * The following is a table specifying the message type and processing code for each transaction type.
 *
 *      Transaction	        Message Type	Processing Code
 *      Authorization	    0100	        00 a0 0x
 *      Balance Inquiry	    0100	        31 a0 0x
 *      Sale	            0200	        00 a0 0x
 *      Cash	            0200	        01 a0 0x
 *      Void	            0200	        02 a0 0x
 *      Mobile Topup	    0200	        57 a0 0x
 *
 */
public enum  ProcessingCode implements IEnumFieldValue<String> {
    PURCHASE("00"),
    CASH_WITHDRAWAL("01"),
    BILL_PAYMENT("17"),
    BALANCE_ENQUIRY("31"),
    CUSTOMER_ENQUIRY("33"),
    FUND_TRANSFER("40"),
    FUND_TRANSFER_DR("46"),
    FUND_TRANSFER_CR("47"),
    PIN_CHANGE("90"),
    MINI_STATEMENT("91"),
    PIN_VERIFICATION("92"),
    PIN2_CHANGE("93"),
    // For ndc purpose only
    CASH_REVERSAL("15"),
    BILL_PAYMENT_VALIDATE("19"),
    PINPAD_PIN_VERIFICATION("94"),
    ACCOUNT_INQUIRY("98"),
    UPDATE_CASSETTE_COUNTERS_1_AND_2("99"),
    // UPDATE_CASSETTE_COUNTERS_3_AND_4("00"),
    NOT_DEFINED("ND"),
    ;

    private String processingCode;

    ProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public static ProcessingCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (ProcessingCode processingCode : values()) {
            if (processingCode.getValue().equals(value))
                return processingCode;
        }

        throw new TypeNotFoundException(ProcessingCode.class.getName()
                + " Error creating instance for Processing Code : " + value);
    }

    @Override
    public String getValue() {
        return this.processingCode;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
