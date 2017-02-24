package com.dpi.financial.ftcom.model.type.terminal;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.atm.journal.ATMConstant;
import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;
import com.dpi.financial.ftcom.utility.regex.RegexMatches;

/**
 *
 */
public enum TerminalMessageType implements IEnumFieldValue<String> {
    COMMUNICATION_ERROR(ATMConstant.ATM_REGEX_COMMUNICATION_ERROR),
    COMMUNICATION_OFFLINE(ATMConstant.ATM_REGEX_COMMUNICATION_OFFLINE),
    GO_IN_SERVICE_COMMAND(ATMConstant.ATM_REGEX_GO_IN_SERVICE_COMMAND),
    GO_OUT_OF_SERVICE_COMMAND(ATMConstant.ATM_REGEX_GO_OUT_OF_SERVICE_COMMAND),
    CARD_JAMMED(ATMConstant.ATM_REGEX_CARD_JAMMED),
    CARD_RETAINED(ATMConstant.ATM_REGEX_CARD_RETAINED),
    CASH_RETRACTED(ATMConstant.ATM_REGEX_CASH_RETRACTED),
    TERMINAL_MESSAGE(ATMConstant.ATM_REGEX_TERMINAL_MESSAGE), //IGNORE, DEFAULT, UNDEFINED
    ;

    private String terminalMessageType;

    public static TerminalMessageType ignore() {
        return TerminalMessageType.TERMINAL_MESSAGE;
    }

    TerminalMessageType(String terminalMessageType) {
        this.terminalMessageType = terminalMessageType;
    }

    public static TerminalMessageType getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (TerminalMessageType terminalMessageType : values()) {
            if (terminalMessageType == ignore())
                continue;

            if (terminalMessageType.getValue().equals(value))
                return terminalMessageType;
        }

        throw new TypeNotFoundException(TerminalMessageType.class.getName()
                + " Error creating instance for terminal message type: " + value);
    }

    public static TerminalMessageType match(String value) throws MultipleMatchException {
        if (value == null || value.isEmpty())
            return null;

        for (TerminalMessageType terminalMessageType : values()) {
            if (terminalMessageType == ignore())
                continue;

            if (RegexMatches.getSingleResult(terminalMessageType.getValue(), value) != null)
                return terminalMessageType;
        }

        return undefined();
    }

    private static TerminalMessageType undefined() {
        return TerminalMessageType.TERMINAL_MESSAGE;
    }

    @Override
    public String getValue() {
        return this.terminalMessageType;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
