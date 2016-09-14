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

    public static ProcessingCode getInstance(String value) {
        for (ProcessingCode processingCode : values()) {
            if (processingCode.getValue().equals(value))
                return processingCode;
        }
        throw new TypeNotFoundException(ProcessingCode.class.getName()
                + " Error creating instance for processing code : " + value);
    }

    @Override
    public String getValue() {
        return this.processingCode;
    }
}
