package com.dpi.financial.ftcom.service.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.meb.isc.reconciliation.SwitchReconciliationService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.SwipeCardDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.TerminalTransactionDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.reconciliation.SwitchReconciliationDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.transaction.SwitchTransactionDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.transaction.SynchronizeStatisticsDao;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SynchronizeStatistics;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.service.base.meb.isc.synchronize.SynchronizeHelper;
import com.dpi.financial.ftcom.service.exception.InvalidSynchronizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Stateless
@LocalBean
@Local(SwitchReconciliationService.class)
public class SwitchReconciliationServiceImpl extends GeneralServiceImpl<MiddleEastBankSwitchTransaction>
        implements SwitchReconciliationService {
    Logger logger = LoggerFactory.getLogger(SwitchReconciliationServiceImpl.class);

    @Resource
    private SessionContext sessionContext;

    @EJB
    private JournalFileService journalFileService;

    @EJB
    private OperationCodeService operationCodeService;

    @EJB
    private SwitchReconciliationDao dao;

    @EJB
    private SwitchTransactionDao switchTransactionDao;

    @EJB
    private SynchronizeStatisticsDao synchronizeStatisticsDao;

    @EJB
    private TerminalTransactionDao terminalTransactionDao;

    @EJB
    SwipeCardDao swipeCardDao;

    // private MiddleEastBankSwitchTransaction[] switchTransactions;
    // private TerminalTransaction[] atmTransactions;

    @Override
    public GenericDao<MiddleEastBankSwitchTransaction> getGenericDao() {
        return dao;
    }

    @Override
    @Asynchronous
    public void synchronizeAtmTransactions(String luno, String cardNumber) {
        logger.info("Getting all terminal transaction for card number: {} on terminal: {}", cardNumber, luno);
    }

    @Override
    public List<String> findAllCard(String luno, Date transactionDateFrom, Date transactionDateTo) {
        logger.info("Find all card number for synchronizing.");
        return dao.findAllCard(luno, transactionDateFrom, transactionDateTo);
    }

    @Override
    public List<MiddleEastBankSwitchTransaction> findInconsistentTransactions(String luno, Date switchTransactionDateFrom, Date switchTransactionDateTo) {
        return dao.findInconsistentTransactions(luno, switchTransactionDateFrom, switchTransactionDateTo);
    }

    @Override
    public void syncByRetrievalReferenceNumber(String luno) {
        dao.syncByRetrievalReferenceNumber(luno);
    }

    // @Schedule(second = "*/10", minute = "*", hour = "*")
    public void synchronizeTimer() {
        List<SynchronizeStatistics> synchronizeStatisticsList = synchronizeStatisticsDao.findAllCashWithdrawalByLuno("01001");
        if (synchronizeStatisticsList == null || synchronizeStatisticsList.size() == 0)
            logger.info("There is nothing to synchronize");

        if (synchronizeStatisticsList != null && synchronizeStatisticsList.size() > 0) {
            SynchronizeStatistics synchronizeStatistics = synchronizeStatisticsList.get(0);
            int countOfRemainSwitchTransaction;

            try {
                countOfRemainSwitchTransaction = sessionContext.getBusinessObject(SwitchReconciliationServiceImpl.class).synchronize("01001", synchronizeStatistics.getCardNumber());
            } catch (Exception e) {
                countOfRemainSwitchTransaction = -1;
                logger.error("Error in synchronizeTimer", e);
            }

            synchronizeStatistics.setRetryCount(synchronizeStatistics.getRetryCount() + 1);
            synchronizeStatistics.setRemainNo(countOfRemainSwitchTransaction);
            synchronizeStatisticsDao.update(synchronizeStatistics);
        }
    }

    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int synchronize(String luno, String cardNumber) {
        logger.info("Synchronizing started for {}/{}", luno, cardNumber);
        int count = -2;
//        try {
            synchronizeAtmTransactionsByRRN(luno, cardNumber);
            count = synchronizeAtmTransactionsByOtherAlgorithms(luno, cardNumber);
            logger.info("Synchronize finished for {}/{}", luno, cardNumber);
            logger.info("--------------------------------------------------");
//        } catch (InvalidSynchronizeException e) {
//            logger.error("Error in synchronize: " + luno + "/" + cardNumber, e);
//        }
        return count;
    }

    private void synchronizeAtmTransactionsByRRN(String luno, String cardNumber) throws InvalidSynchronizeException {
        logger.info("Start synchronizing by RNN ...");

        List<MiddleEastBankSwitchTransaction> switchTransactions = switchTransactionDao.findAllByLunoCardNumber(luno, cardNumber);
        List<TerminalTransaction> terminalTransactions = terminalTransactionDao.findAllByLunoCardNumber(luno, cardNumber);

        // Sync by RRN
        for (MiddleEastBankSwitchTransaction switchTransaction : switchTransactions) {

            if (switchTransaction.getTerminalTransaction() != null) {
                if (switchTransaction.getRrn() != null && !switchTransaction.getRrn().equals(switchTransaction.getTerminalTransaction().getRrn()))
                    throw new InvalidSynchronizeException("Switch transaction and terminal transaction has different rrn: " + switchTransaction.getRrn());

                continue;
            }

            Iterator<TerminalTransaction> terminalTransactionIterator = terminalTransactions.iterator();

            while (terminalTransactionIterator.hasNext()) {
                TerminalTransaction terminalTransaction = terminalTransactionIterator.next();
                if (switchTransaction.getRrn() != null && switchTransaction.getRrn().equals(terminalTransaction.getRrn())) {
                    switchTransaction.setTerminalTransaction(terminalTransaction);
                    dao.update(switchTransaction);
                    logger.info("Switch transaction bound to atm transaction with rrn: " + switchTransaction.getRrn());
                    break;
                }
            }
        }
        logger.info("Finished synchronizing by RNN ...");
    }

    /**
     * Synchronize by restriction
     *
     * @param luno
     * @param cardNumber
     */
    private int synchronizeAtmTransactionsByOtherAlgorithms(String luno, String cardNumber) {
        try {
            List<MiddleEastBankSwitchTransaction> switchTransactions = switchTransactionDao.findAllByLunoCardNumber(luno, cardNumber);
            List<TerminalTransaction> terminalTransactions = terminalTransactionDao.findAllByLunoCardNumber(luno, cardNumber);
            SynchronizeHelper synchronizeHelper = new SynchronizeHelper(switchTransactions, terminalTransactions);

            boolean matchAnyTransaction;
            do {
                int phase = 0;
                logger.info("Start synchronizing by other algorithms ... ({})", phase++);
                matchAnyTransaction = false;
                for (MiddleEastBankSwitchTransaction switchTransaction : switchTransactions) {
                    if (switchTransaction.getTerminalTransaction() == null) {
                        List<MiddleEastBankSwitchTransaction> similarSwitchTransactionList = synchronizeHelper.getSimilarSwitchTransactions(switchTransaction);
                        List<TerminalTransaction> probabilityAtmTransactionList = synchronizeHelper.getProbabilityTerminalTransactionRestrictByAmount(switchTransaction);

                        matchAnyTransaction = synchronizeSameCountList(similarSwitchTransactionList, probabilityAtmTransactionList);
                        matchAnyTransaction = matchAnyTransaction || synchronizeSuccessfulCashWithdrawal(similarSwitchTransactionList, probabilityAtmTransactionList);
                    }
                }
            } while (matchAnyTransaction);
            logger.info("Finished synchronizing by other algorithms ...");
            long count = switchTransactions.stream().filter(item -> item.getTerminalTransaction() == null).count();
            return Math.toIntExact(count);
        } catch (Exception e) {
            logger.error("Error in synchronizeAtmTransactionsByOtherAlgorithms", e);
        }
        return -1;
    }

    private boolean synchronizeSameCountList(List<MiddleEastBankSwitchTransaction> similarSwitchTransactionList, List<TerminalTransaction> probabilityAtmTransactionList) {
        boolean any = false;
        if (similarSwitchTransactionList.size() == probabilityAtmTransactionList.size()) {
            for (int i = 0; i < similarSwitchTransactionList.size(); i++) {
                TerminalTransaction atmTransaction = probabilityAtmTransactionList.get(i);
                MiddleEastBankSwitchTransaction switchTransaction = similarSwitchTransactionList.get(i);

                switchTransaction.setTerminalTransaction(atmTransaction);
                dao.update(switchTransaction);

                // atmTransaction.setSwitchTransaction(switchTransaction);
                atmTransaction.setRetrievedRrn(switchTransaction.getRrn());
                terminalTransactionDao.update(atmTransaction);

                System.out.println(
                        MessageFormat.format("Switch transaction with rrn : {0} bound to {1}/{2}:{3}",
                                switchTransaction.getRrn(), atmTransaction.getLuno(),
                                atmTransaction.getFileName(), atmTransaction.getLineStart())
                );
                any = true;
            }
        }
        return any;
    }

    private boolean synchronizeSuccessfulCashWithdrawal(List<MiddleEastBankSwitchTransaction> similarSwitchTransactionList, List<TerminalTransaction> probabilityAtmTransactionList) {
        boolean any = false;
        if (similarSwitchTransactionList.size() == 1) {
            MiddleEastBankSwitchTransaction switchTransaction = similarSwitchTransactionList.get(0);
            if (switchTransaction.getResponseCode() != null
                    && switchTransaction.getResponseCode().equals("00")
                    && !switchTransaction.getReveresed().equals(Boolean.TRUE)) {
                int cashTakenCount = 0;
                TerminalTransaction successfulCashWithdrawalOnATM = null;
                for (TerminalTransaction terminalTransaction : probabilityAtmTransactionList) {
                    if (terminalTransaction.getCashTaken() != null
                            && terminalTransaction.getCashTaken() == YesNoType.Yes) {
                        cashTakenCount++;
                        successfulCashWithdrawalOnATM = terminalTransaction;
                    }
                }
                if (cashTakenCount == 1) {
                    switchTransaction.setTerminalTransaction(successfulCashWithdrawalOnATM);
                    dao.update(switchTransaction);

                    successfulCashWithdrawalOnATM.setRetrievedRrn(switchTransaction.getRrn());
                    terminalTransactionDao.update(successfulCashWithdrawalOnATM);

                    logger.info("Switch transaction with rrn : {} bound to {}/{}:{}",
                            switchTransaction.getRrn(),
                            successfulCashWithdrawalOnATM.getLuno(),
                            successfulCashWithdrawalOnATM.getFileName(),
                            successfulCashWithdrawalOnATM.getLineStart()
                    );
                    any = true;
                }
            }
        }
        return any;
    }


}