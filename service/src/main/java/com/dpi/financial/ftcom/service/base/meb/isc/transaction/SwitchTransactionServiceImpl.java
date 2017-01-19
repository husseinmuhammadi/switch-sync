package com.dpi.financial.ftcom.service.base.meb.isc.transaction;

import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.meb.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.SwipeCardDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.TerminalTransactionDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.transaction.SwitchTransactionDao;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

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

    public List<SwitchTransaction> findAllByLunoCardNumber(String luno, String cardNumber) {
        return dao.findAllByLunoCardNumber(luno, cardNumber);
    }
}
