package com.en.std.definition;

import com.en.std.exception.TypeNotFoundException;


/**
 * Created by h.mohammadi on 6/15/2016.
 */
public enum ProcessingCode implements IEnumFieldValue<String> {
    Purchase("00"),
    CashWithdrawal("01"),
    BillPayment("17"),
    BalanceEnquiry("31"),
    CustomerEnquiry("33"),
    FundTransfer("40"),
    FundTransferDr("46"),
    FundTransferCr("47"),
    PinChange("90"),
    MiniStatement("91"),
    PinVerification("92");

    private String processingCode;

    ProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public static ProcessingCode getInstance(String p) {
        for (ProcessingCode processingCode : values()) {
            if (processingCode.getValue().equals(p))
                return processingCode;
        }
        throw new TypeNotFoundException(FinancialServiceProvider.class.getName()
                + " Processing Code :" + p);
    }

    @Override
    public String getValue() {
        return this.processingCode;
    }
}
