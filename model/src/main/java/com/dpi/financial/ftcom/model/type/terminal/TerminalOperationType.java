package com.dpi.financial.ftcom.model.type.terminal;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.atm.journal.ATMConstant;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * ADD ALL PHRASES YOU THINK IT IS IMPORTANT ON JOURNAL HERE
 * DON'T WORRY ABOUT WHAT HAPPEN
 * SELECT ID, CURRENT_STATE, OPERATION_TYPE, FOLLOWING_STATE, USER_ACTION FROM TERMINAL_OPERATION_STATE WHERE FOLLOWING_STATE = 'N';
 * ALL THINGS YOU SEE HERE SHOULD CHANGE THIS STATE TO ONE OF TerminalTransactionState
 * AND WHAT EVER YOU ENTER IN USER_ACTION FIELD SHOULD DEFINE IN UserAction
 * AFTER THAT IN METHOD BELOW
 * com.dpi.financial.ftcom.web.controller.base.meb.atm.journal.JournalFileManager#prepareAtmTransactions(com.dpi.financial.ftcom.model.to.atm.Terminal)
 * YOU CAN WRITE YOUR CODE WHEN A SPECFIC LINE READ
 */
public enum TerminalOperationType implements IEnumFieldValue<String> {
    TRANSACTION_START(ATMConstant.ATM_REGEX_TRANSACTION_START),
    TRACK_DATA(ATMConstant.ATM_REGEX_TRACK_DATA),
    PIN_ENTERED(ATMConstant.ATM_REGEX_PIN_ENTERED),
    INFORMATION_ENTERED(ATMConstant.ATM_REGEX_INFORMATION_ENTERED),
    AMOUNT_ENTERED(ATMConstant.ATM_REGEX_AMOUNT_ENTERED),
    TRANSACTION_REQUEST(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST),
    TRANSACTION_REPLY(ATMConstant.ATM_REGEX_TRANSACTION_REPLY),
    RECEIPT_FUND_TRANSFER(ATMConstant.ATM_REGEX_FUND_TRANSFER),
    RECEIPT_CASH_WITHDRAWAL(ATMConstant.ATM_REGEX_CASH_WITHDRAWAL),
    RECEIPT_BALANCE_INQUIRY(ATMConstant.ATM_REGEX_BALANCE_INQUIRY),
    RECEIPT_CUSTOMER_INQUIRY(ATMConstant.ATM_REGEX_CUSTOMER_INQUIRY),
    RECEIPT_MINI_STATEMENT(ATMConstant.ATM_REGEX_MINI_STATEMENT),
    RECEIPT_PIN_CHNAGE(ATMConstant.ATM_REGEX_PIN_CHNAGE),
    CASH_REQUEST(ATMConstant.ATM_REGEX_CASH_REQUEST),
    CASH(ATMConstant.ATM_REGEX_CASH),
    CARD_TAKEN(ATMConstant.ATM_REGEX_CARD_TAKEN),
    CASH_PRESENTED(ATMConstant.ATM_REGEX_CASH_PRESENTED),
    CASH_TAKEN(ATMConstant.ATM_REGEX_CASH_TAKEN),
    TRANSACTION_END(ATMConstant.ATM_REGEX_TRANSACTION_END),
    TERMINAL_MESSAGE(ATMConstant.ATM_REGEX_TERMINAL_MESSAGE),

    IGNORE("IGNORE"),
    // INVALID_TYPE("INVALID TYPE"), // NOT FOUND or IGNORE TYPE or DEFAULT TYPE
    ;

    private String terminalOperationType;

    TerminalOperationType(String terminalOperationType) {
        this.terminalOperationType = terminalOperationType;
    }

    public static TerminalOperationType getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (TerminalOperationType terminalOperationType : values()) {
            if (terminalOperationType.getValue().equals(value))
                return terminalOperationType;
        }

        throw new TypeNotFoundException(TerminalOperationType.class.getName()
                + " Error creating instance for terminal operation type: " + value);
    }

    @Override
    public String getValue() {
        return this.terminalOperationType;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
