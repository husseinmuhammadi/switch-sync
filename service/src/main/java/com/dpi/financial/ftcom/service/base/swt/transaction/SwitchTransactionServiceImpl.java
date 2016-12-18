package com.dpi.financial.ftcom.service.base.swt.transaction;

import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.api.base.swt.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.transaction.SwipeCardDao;
import com.dpi.financial.ftcom.model.dao.atm.transaction.TerminalTransactionDao;
import com.dpi.financial.ftcom.model.dao.swt.transaction.SwitchTransactionDao;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.service.exception.atm.transaction.InvalidAmountException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Stateless
@Local(SwitchTransactionService.class)
public class SwitchTransactionServiceImpl extends GeneralServiceImpl<SwitchTransaction>
        implements SwitchTransactionService {

    @EJB
    private JournalFileService journalFileService;

    @EJB
    private OperationCodeService operationCodeService;

    @EJB
    private SwitchTransactionDao dao;

    @EJB
    private TerminalTransactionDao terminalTransactionDao;

    @EJB
    SwipeCardDao swipeCardDao;

    private SwitchTransaction[] switchTransactions;
    private TerminalTransaction[] atmTransactions;

    @Override
    public GenericDao<SwitchTransaction> getGenericDao() {
        return dao;
    }

    public List<SwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber){
        return dao.findAllByLunoCardNumber(luno, cardNumber);
    }
}
