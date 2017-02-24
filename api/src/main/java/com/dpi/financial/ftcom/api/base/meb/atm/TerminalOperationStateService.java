package com.dpi.financial.ftcom.api.base.meb.atm;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.TerminalOperationState;
import com.dpi.financial.ftcom.model.type.terminal.TerminalOperationType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;

import java.util.Date;
import java.util.List;

public interface TerminalOperationStateService extends GeneralServiceApi<TerminalOperationState> {
    List<TerminalOperationState> findAll();
    TerminalOperationState findByStateAndOperation(TerminalTransactionState currentState, TerminalOperationType operationType);
}
