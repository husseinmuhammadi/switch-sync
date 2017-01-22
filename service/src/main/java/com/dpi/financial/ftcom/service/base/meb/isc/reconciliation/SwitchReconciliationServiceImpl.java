package com.dpi.financial.ftcom.service.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.meb.isc.reconciliation.SwitchReconciliationService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.SwipeCardDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.TerminalTransactionDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.reconciliation.SwitchReconciliationDao;
import com.dpi.financial.ftcom.model.dao.meb.isc.transaction.SwitchTransactionDao;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
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
@Local(SwitchReconciliationService.class)
public class SwitchReconciliationServiceImpl extends GeneralServiceImpl<MiddleEastBankSwitchTransaction>
        implements SwitchReconciliationService {

    @EJB
    private JournalFileService journalFileService;

    @EJB
    private OperationCodeService operationCodeService;

    @EJB
    private SwitchReconciliationDao dao;

    @EJB
    private SwitchTransactionDao switchTransactionDao;

    @EJB
    private TerminalTransactionDao terminalTransactionDao;

    @EJB
    SwipeCardDao swipeCardDao;

    private MiddleEastBankSwitchTransaction[] switchTransactions;
    private TerminalTransaction[] atmTransactions;

    @Override
    public GenericDao<MiddleEastBankSwitchTransaction> getGenericDao() {
        return dao;
    }

    @Override
    public void synchronizeAtmTransactions(String luno, String cardNumber) {
        List<MiddleEastBankSwitchTransaction> switchTransactionList = switchTransactionDao.findAllByLunoCardNumber(luno, cardNumber);
        List<TerminalTransaction> terminalTransactionList = terminalTransactionDao.findAllByLunoCardNumber(luno, cardNumber);

        switchTransactions = new MiddleEastBankSwitchTransaction[switchTransactionList.size()];
        switchTransactionList.toArray(switchTransactions);
        for (int i = 0; i < switchTransactions.length; i++)
            switchTransactions[i].setIndex(i);

        atmTransactions = new TerminalTransaction[terminalTransactionList.size()];
        terminalTransactionList.toArray(atmTransactions);
        for (int i = 0; i < atmTransactions.length; i++)
            atmTransactions[i].setIndex(i);

        synchronize();
    }

    @Override
    public List<String> findAllCard(String luno, Date transactionDateFrom, Date transactionDateTo) {
        return dao.findAllCard(luno, transactionDateFrom, transactionDateTo);
    }

    @Override
    public List<MiddleEastBankSwitchTransaction> findInconsistentTransactions(String luno, Date switchTransactionDateFrom, Date switchTransactionDateTo) {
        return dao.findInconsistentTransactions(luno, switchTransactionDateFrom, switchTransactionDateTo);
    }

    private void synchronize() {

        // Sync by RRN
        for (int i = 0; i < switchTransactions.length; i++) {
            MiddleEastBankSwitchTransaction switchTransaction = switchTransactions[i];

            int start = 0;
            if (i > 0)
                start = switchTransactions[i - 1].getAtmTransactionIndex() + 1;
            for (int j = start; j < atmTransactions.length; j++) {
                TerminalTransaction atmTransaction = atmTransactions[j];
                String atmRrn = atmTransaction.getRrn();
                if (atmTransaction.getRrn() != null && atmTransaction.getRrn().equals(switchTransaction.getRrn())) {
                    switchTransaction.setAtmTransactionIndex(j);
                    switchTransaction.setTerminalTransaction(atmTransaction);
                    dao.update(switchTransaction);

                    atmTransaction.switchTransactionIndex = i;
                    atmTransaction.setSwitchTransaction(switchTransaction);
                    terminalTransactionDao.update(atmTransaction);
                    break;
                }
            }
        }

        // Continue till there is no transaction match
        boolean matchAnyTransaction;
        do {
            matchAnyTransaction = false;
            for (int cur = 0; cur < switchTransactions.length; cur++) {
                MiddleEastBankSwitchTransaction switchTransaction = switchTransactions[cur];
                if (switchTransaction.getTerminalTransaction() == null) {
                    List<MiddleEastBankSwitchTransaction> similarSwitchTransactionList = new ArrayList<MiddleEastBankSwitchTransaction>();

                    int last = cur;
                    while (last < switchTransactions.length
                            && Objects.equals(switchTransactions[last].getPrice(), switchTransaction.getPrice())
                            && switchTransactions[last].getTerminalTransaction() == null) {
                        similarSwitchTransactionList.add(switchTransactions[last]);
                        last++;
                    }

                    System.out.println("All probability for switch transaction: " + switchTransaction.getRrn());
                    List<TerminalTransaction> probabilityAtmTransactionList = getProbabilityAtmTransactionRestrictByAmount(cur);

                    if (similarSwitchTransactionList.size() == probabilityAtmTransactionList.size()) {
                        for (int i = 0; i < similarSwitchTransactionList.size(); i++) {
                            TerminalTransaction atmTransaction = probabilityAtmTransactionList.get(i);
                            MiddleEastBankSwitchTransaction switchTransaction1 = similarSwitchTransactionList.get(i);

                            switchTransaction1.setAtmTransactionIndex(atmTransaction.getIndex());
                            switchTransaction1.setTerminalTransaction(atmTransaction);
                            dao.update(switchTransaction1);

                            atmTransaction.setSwitchTransaction(switchTransaction1);
                            atmTransaction.setRetrievedRrn(switchTransaction1.getRrn());
                            terminalTransactionDao.update(atmTransaction);

                            System.out.println(
                                    MessageFormat.format("Switch transaction with rrn : {0} bound to {1}/{2}:{3}",
                                            switchTransaction1.getRrn(), atmTransaction.getLuno(),
                                            atmTransaction.getFileName(), atmTransaction.getLineStart())
                            );
                            matchAnyTransaction = true;
                        }
                    }
                    if (similarSwitchTransactionList.size() == 1
                            && switchTransaction.getResponseCode() != null
                            && switchTransaction.getResponseCode().equals("00")
                            && !switchTransaction.getReveresed().equals(Boolean.TRUE)) {
                        int cashTakenCount = 0;
                        int cashTakenIndex = -1;
                        for (TerminalTransaction atmTransaction : probabilityAtmTransactionList) {
                            if (atmTransaction.getCashTaken() != null
                                    && atmTransaction.getCashTaken().equals("Y")) {
                                cashTakenCount++;
                                cashTakenIndex = atmTransaction.getIndex();
                            }
                        }
                        if (cashTakenCount == 1) {
                            TerminalTransaction atmTransaction = atmTransactions[cashTakenIndex];
                            switchTransaction.setAtmTransactionIndex(atmTransaction.getIndex());
                            switchTransaction.setTerminalTransaction(atmTransaction);
                            dao.update(switchTransaction);

                            atmTransaction.setSwitchTransaction(switchTransaction);
                            atmTransaction.setRetrievedRrn(switchTransaction.getRrn());
                            terminalTransactionDao.update(atmTransaction);

                            System.out.println(
                                    MessageFormat.format("Switch transaction with rrn : {0} bound to {1}/{2}:{3}",
                                            switchTransaction.getRrn(), atmTransaction.getLuno(),
                                            atmTransaction.getFileName(), atmTransaction.getLineStart())
                            );
                            matchAnyTransaction = true;
                        }
                    }
                }
            }

        } while (matchAnyTransaction);
    }

    private List<TerminalTransaction> getProbabilityAtmTransactionRestrictByAmount(int switchIndex) throws InvalidAmountException {
        MiddleEastBankSwitchTransaction switchTransaction = switchTransactions[switchIndex];
        List<TerminalTransaction> atmTransactionList = new ArrayList<TerminalTransaction>();
        List<TerminalTransaction> probability = getProbabilityAtmTransaction(switchIndex);
        for (TerminalTransaction atmTransaction : probability) {
            if (atmTransaction.getFastCashAmount() != null && atmTransaction.getAmountEntered() != null
                    && !Objects.equals(atmTransaction.getFastCashAmount(), atmTransaction.getAmountEntered()))
                throw new InvalidAmountException();
            BigDecimal amount = atmTransaction.getFastCashAmount();
            if (amount == null)
                amount = atmTransaction.getAmountEntered();
            if (Objects.equals(switchTransaction.getPrice(), amount)) {
                atmTransactionList.add(atmTransaction);
            }
        }
        return atmTransactionList;
    }

    private List<TerminalTransaction> getProbabilityAtmTransaction(int switchIndex) {
        MiddleEastBankSwitchTransaction switchTransaction = switchTransactions[switchIndex];

        List<TerminalTransaction> atmTransactionList = new ArrayList<TerminalTransaction>();

        if (switchTransaction.getTerminalTransaction() != null) {
            atmTransactionList.add(atmTransactions[switchTransaction.getAtmTransactionIndex()]);
            // out(atmTransactions[switchTransaction.atmTransactionIndex]);
        } else {
            int start = getFormerIndex(switchIndex);
            int end = getFollowIndex(switchIndex);

            start++;

            for (int index = start; index < end; index++) {
                atmTransactionList.add(atmTransactions[index]);
            }
        }
        return atmTransactionList;
    }

    int getFormerIndex(int switchIndex) {
        switchIndex--;
        if (switchIndex < 0)
            return -1;
        MiddleEastBankSwitchTransaction switchTransaction = switchTransactions[switchIndex];
        if (switchTransaction.getTerminalTransaction() == null)
            return getFormerIndex(switchIndex);
        return switchTransaction.getAtmTransactionIndex();
    }

    int getFollowIndex(int switchIndex) {
        switchIndex++;
        if (switchIndex >= switchTransactions.length)
            return atmTransactions.length;
        MiddleEastBankSwitchTransaction switchTransaction = switchTransactions[switchIndex];
        if (switchTransaction.getTerminalTransaction() == null)
            return getFollowIndex(switchIndex);
        return switchTransaction.getAtmTransactionIndex();
    }
}
