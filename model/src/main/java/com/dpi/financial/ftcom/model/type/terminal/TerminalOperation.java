package com.dpi.financial.ftcom.model.type.terminal;

import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;
import com.dpi.financial.ftcom.utility.regex.RegexMatches;

/**
 * Created by h.mohammadi on 2/20/2017.
 */
public class TerminalOperation {
    private final TerminalOperationType terminalOperationType;

    protected TerminalOperation(TerminalOperationType terminalOperationType) {
        this.terminalOperationType = terminalOperationType;
    }

    public TerminalOperationType getTerminalOperationType() {
        return terminalOperationType;
    }

    public static TerminalOperation createInstance(String operation) throws MultipleMatchException {
        for (TerminalOperationType terminalOperationType : TerminalOperationType.values()) {
            if (terminalOperationType == TerminalOperationType.IGNORE)
                continue;

            if (RegexMatches.getSingleResult(terminalOperationType.getValue(), operation) != null) {
                return new TerminalOperation(terminalOperationType);
            }
        }

        return new TerminalOperation(TerminalOperationType.IGNORE);
    }
}
