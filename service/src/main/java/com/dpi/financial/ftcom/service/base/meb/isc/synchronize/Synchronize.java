package com.dpi.financial.ftcom.service.base.meb.isc.synchronize;

import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
import com.dpi.financial.ftcom.service.base.meb.isc.reconciliation.SwitchReconciliationServiceImpl;
import com.dpi.financial.ftcom.service.exception.atm.transaction.InvalidAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class Synchronize {
    Logger logger = LoggerFactory.getLogger(Synchronize.class);

    private final List<MiddleEastBankSwitchTransaction> switchTransactions;
    private final List<TerminalTransaction> terminalTransactions;

    public Synchronize(List<MiddleEastBankSwitchTransaction> switchTransactions, List<TerminalTransaction> terminalTransactions) {
        this.switchTransactions = switchTransactions;
        this.terminalTransactions = terminalTransactions;
    }

    public List<MiddleEastBankSwitchTransaction> getSimilarSwitchTransactions(MiddleEastBankSwitchTransaction switchTransaction) {
        List<MiddleEastBankSwitchTransaction> similarSwitchTransactionList = new ArrayList<>();
        ListIterator<MiddleEastBankSwitchTransaction> iterator = switchTransactions.listIterator(switchTransactions.indexOf(switchTransaction));
        while (iterator.hasNext()) {
            MiddleEastBankSwitchTransaction next = iterator.next();
            if (next.getTerminalTransaction() == null
                    && switchTransaction.getPrice() != null
                    && switchTransaction.getPrice().equals(next.getPrice())) {
                similarSwitchTransactionList.add(next);
            }
        }

        logger.info("There is {} transaction in switch that are similar", similarSwitchTransactionList.size());
        return similarSwitchTransactionList;
    }

    public List<TerminalTransaction> getProbabilityTerminalTransaction(MiddleEastBankSwitchTransaction switchTransaction) {
        if (switchTransaction.getTerminalTransaction() != null)
            return Collections.singletonList(switchTransaction.getTerminalTransaction());

        int startSwitchTransactionIndex = getFormerSwitchTransactionIndex(switchTransaction);
        int stopSwitchTransactionIndex = getFollowSwitchTransactionIndex(switchTransaction);

        int startTerminalTransactionIndex = 0;
        if (startSwitchTransactionIndex >= 0) {
            startTerminalTransactionIndex = terminalTransactions.indexOf(switchTransactions.get(startSwitchTransactionIndex).getTerminalTransaction());
        }

        int stopTerminalTransactionIndex = terminalTransactions.size() - 1;
        if (startSwitchTransactionIndex < terminalTransactions.size()) {
            stopTerminalTransactionIndex = terminalTransactions.indexOf(switchTransactions.get(stopSwitchTransactionIndex).getTerminalTransaction());
        }

        return terminalTransactions.subList(startTerminalTransactionIndex, stopTerminalTransactionIndex);
    }

    public List<TerminalTransaction> getProbabilityTerminalTransactionRestrictByAmount(MiddleEastBankSwitchTransaction switchTransaction) throws InvalidAmountException {
        logger.info("Find all probability for switch transaction: {}", switchTransaction.getRrn());
        List<TerminalTransaction> probability = getProbabilityTerminalTransaction(switchTransaction);

        List<TerminalTransaction> atmTransactionList = new ArrayList<TerminalTransaction>();
        for (TerminalTransaction atmTransaction : probability) {
            if (atmTransaction.getFastCashAmount() != null && atmTransaction.getAmountEntered() != null
                    && !Objects.equals(atmTransaction.getFastCashAmount(), atmTransaction.getAmountEntered()))
                throw new InvalidAmountException();

            BigDecimal amount = atmTransaction.getFastCashAmount() != null ? atmTransaction.getFastCashAmount() : atmTransaction.getAmountEntered();
            if (Objects.equals(switchTransaction.getPrice(), amount)) {
                atmTransactionList.add(atmTransaction);
            }
        }
        return atmTransactionList;
    }


    private int getFormerSwitchTransactionIndex(MiddleEastBankSwitchTransaction switchTransaction) {
        ListIterator<MiddleEastBankSwitchTransaction> iterator = switchTransactions.listIterator(switchTransactions.indexOf(switchTransaction));

        if (!iterator.hasPrevious())
            return -1;

        MiddleEastBankSwitchTransaction previous = iterator.previous();

        if (previous.getTerminalTransaction() == null)
            return getFormerSwitchTransactionIndex(previous);

        return switchTransactions.indexOf(previous);
    }

    private int getFollowSwitchTransactionIndex(MiddleEastBankSwitchTransaction switchTransaction) {
        ListIterator<MiddleEastBankSwitchTransaction> iterator = switchTransactions.listIterator(switchTransactions.indexOf(switchTransaction));

        if (!iterator.hasNext())
            return switchTransactions.size();

        MiddleEastBankSwitchTransaction next = iterator.previous();

        if (next.getTerminalTransaction() == null)
            return getFollowSwitchTransactionIndex(next);

        return switchTransactions.indexOf(next);
    }
}
