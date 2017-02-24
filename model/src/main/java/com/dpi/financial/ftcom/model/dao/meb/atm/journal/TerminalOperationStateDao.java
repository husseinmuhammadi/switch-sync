package com.dpi.financial.ftcom.model.dao.meb.atm.journal;

import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.TerminalOperationState;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.TerminalOperationState;
import com.dpi.financial.ftcom.model.type.terminal.TerminalOperationType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TerminalOperationStateDao extends GenericDao<TerminalOperationState> {
    Logger logger = LoggerFactory.getLogger(TerminalOperationStateDao.class);

    public TerminalOperationStateDao() {
        super(TerminalOperationState.class);
    }

    public List<TerminalOperationState> findAll() {
        return createNamedQuery(TerminalOperationState.FIND_ALL).getResultList();
    }

    public TerminalOperationState findByStateAndOperation(TerminalTransactionState currentState, TerminalOperationType operationType) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("currentState", currentState);
        parameters.put("operationType", operationType);
        return createNamedQuery(TerminalOperationState.FIND_BY_STATE_AND_OPERATION, parameters).getSingleResult();
    }
}
