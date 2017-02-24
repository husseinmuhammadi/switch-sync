package com.dpi.financial.ftcom.service.base.meb.atm.journal;

import com.dpi.financial.ftcom.api.base.meb.atm.TerminalOperationStateService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.journal.TerminalOperationStateDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.TerminalOperationState;
import com.dpi.financial.ftcom.model.type.terminal.TerminalOperationType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Local(TerminalOperationStateService.class)
public class TerminalOperationStateServiceImpl extends GeneralServiceImpl<TerminalOperationState>
        implements TerminalOperationStateService {

    Logger logger = LoggerFactory.getLogger(TerminalOperationStateServiceImpl.class);

    @EJB
    private TerminalOperationStateDao dao;

    @Override
    public GenericDao<TerminalOperationState> getGenericDao() {
        return dao;
    }

    @Override
    public List<TerminalOperationState> findAll() {
        return dao.findAll();
    }

    @Override
    public TerminalOperationState findByStateAndOperation(TerminalTransactionState currentState, TerminalOperationType operationType) {
        return dao.findByStateAndOperation(currentState, operationType);
    }
}
