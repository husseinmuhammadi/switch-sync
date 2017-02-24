package com.dpi.financial.ftcom.model.type.terminal.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum TerminalTransactionState implements IEnumFieldValue<String> {
    IDLE("I"),
    TRANSACTION_START("S"), // SWIPE_CARD
    PIN_ENTERED("P"),
    TRANSACTION_REQUEST("R"),
    RECEIPT("C"),
    TRANSACTION_END("E"),
    NOT_DEFINED("N"), // NOT DEFINED, UNDEFINED

    /*
    TRANSACTION_START("TS"),
    TRACK_DATA("TD"),
    PIN_ENTERED("PE"),
    AMOUNT_ENTERED("AE"),
    INFORMATION_ENTERED("IE"),
    TRANSACTION_REQUEST("RQ"),
    TRANSACTION_REPLY("RP"),
    CARD_TAKEN("CT"),
    CASH_PRESENTED("CP"),
    CASH_TAKEN("CS"),
    TRANSACTION_END("TE"),

    INTERACTIVE_TRANSACTION_REPLY(""),
    DOCUMENTS_TAKEN(""),
    CASH_REQUEST(""),
    ENVELOPE_DEPOSITED(""),
    TRANSACTION_FAILED(""),
    WRONG_PIN_ENTERED(""),
    REJECT_CASSETTE_FULL(""),
    CASH_RETRACTED(""),
    APPLICATION_STARTED(""),
    COMMUNICATION_NOT_STARTED(""),
    SOP_SHUTDOWN_REQUEST(""),
    CASH_COUNTERS_BEFORE_SOP(""),
    CASH_COUNTERS_AFTER_SOP(""),
    CASH_COUNTERS(""),
    DEVIC_STATUS_SUPPLY(""),
    UPS_ACTIVE(""),
    UPS_DEACTIVE(""),
    SUPPLY_COUNTERS(""),
    */
    ;

    private String terminalTransactionState;

    TerminalTransactionState(String terminalTransactionState) {
        this.terminalTransactionState = terminalTransactionState;
    }

    public static TerminalTransactionState getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (TerminalTransactionState terminalTransactionState : values()) {
            if (terminalTransactionState.getValue().equals(value))
                return terminalTransactionState;
        }

        throw new TypeNotFoundException(TerminalTransactionState.class.getName()
                + " Error creating instance for terminal transaction state: " + value);
    }

    @Override
    public String getValue() {
        return this.terminalTransactionState;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
