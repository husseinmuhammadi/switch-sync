package com.dpi.financial.ftcom.service.base.meb.isc.synchronize;

import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
import com.dpi.financial.ftcom.service.exception.atm.transaction.InvalidAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

public class SynchronizeHelper {
    Logger logger = LoggerFactory.getLogger(SynchronizeHelper.class);

    private final List<MiddleEastBankSwitchTransaction> switchTransactions;
    private final List<TerminalTransaction> terminalTransactions;

    public SynchronizeHelper(List<MiddleEastBankSwitchTransaction> switchTransactions, List<TerminalTransaction> terminalTransactions) {
        this.switchTransactions = switchTransactions;
        this.terminalTransactions = terminalTransactions;
    }

    public List<MiddleEastBankSwitchTransaction> getSimilarSwitchTransactions(MiddleEastBankSwitchTransaction switchTransaction) {
        List<MiddleEastBankSwitchTransaction> similarSwitchTransactionList = new ArrayList<>();
        ListIterator<MiddleEastBankSwitchTransaction> iterator = switchTransactions.listIterator(switchTransactions.indexOf(switchTransaction));
        while (iterator.hasNext()) {
            MiddleEastBankSwitchTransaction next = iterator.next();

            // Break if the next transaction is already bounded
            if (next.getTerminalTransaction() != null)
                break;

            // Continue to find next unbound transaction
            if (next.getTerminalTransaction() == null
                    && switchTransaction.getPrice() != null
                    && switchTransaction.getPrice().equals(next.getPrice())) {
                similarSwitchTransactionList.add(next);
            }
        }

        StringBuilder builder = new StringBuilder();
        similarSwitchTransactionList.forEach(item -> {
            builder.append(item.getRrn()).append(",");
        });
        if (similarSwitchTransactionList.size() == 1)
            logger.info("Switch transaction to synchronize {}", builder.toString().substring(0, builder.toString().length() - 1));
        if (similarSwitchTransactionList.size() > 1)
            logger.info("Similar switch transactions to synchronize {}", builder.toString().substring(0, builder.toString().length() - 1));
        return similarSwitchTransactionList;
    }

    public List<TerminalTransaction> getProbabilityTerminalTransaction(MiddleEastBankSwitchTransaction switchTransaction) {
        if (switchTransaction.getTerminalTransaction() != null)
            return Collections.singletonList(switchTransaction.getTerminalTransaction());

        int startSwitchTransactionIndex = getFormerSwitchTransactionIndex(switchTransaction);
        int stopSwitchTransactionIndex = getFollowSwitchTransactionIndex(switchTransaction);

        int startTerminalTransactionIndex = -1;
        if (startSwitchTransactionIndex >= 0) {
            startTerminalTransactionIndex = terminalTransactions.indexOf(switchTransactions.get(startSwitchTransactionIndex).getTerminalTransaction());
        }

        int stopTerminalTransactionIndex = terminalTransactions.size() - 1;
        if (stopSwitchTransactionIndex < switchTransactions.size()) {
            stopTerminalTransactionIndex = terminalTransactions.indexOf(switchTransactions.get(stopSwitchTransactionIndex).getTerminalTransaction());
        }

        return terminalTransactions.subList(startTerminalTransactionIndex + 1, stopTerminalTransactionIndex);
    }

    public List<TerminalTransaction> getProbabilityTerminalTransactionRestrictByAmount(MiddleEastBankSwitchTransaction switchTransaction) throws InvalidAmountException {
        logger.info("Find all probability for switch transaction for switch transaction {}", switchTransaction.getRrn());
        List<TerminalTransaction> probability = getProbabilityTerminalTransaction(switchTransaction);
        logger.info("{} row found in terminal transaction.", probability.size());

        List<TerminalTransaction> atmTransactionList = new ArrayList<TerminalTransaction>();
        for (TerminalTransaction atmTransaction : probability) {

            // If both of fast cash amount and amount entered has value they should be same
            if (atmTransaction.getFastCashAmount() != null && atmTransaction.getAmountEntered() != null
                    && !Objects.equals(atmTransaction.getFastCashAmount(), atmTransaction.getAmountEntered())) {
                throw new InvalidAmountException();
            }

            // If switch transaction and atm transaction come with different amount they are not same
            BigDecimal amount = atmTransaction.getFastCashAmount() != null ? atmTransaction.getFastCashAmount() : atmTransaction.getAmountEntered();
            if (!Objects.equals(switchTransaction.getPrice(), amount)) {
                continue;
            }

            // If switch transaction and atm transaction come with different rrn they are not same
            if (atmTransaction.getRrn() != null && switchTransaction.getRrn() != null
                    && !Objects.equals(atmTransaction.getRrn(), switchTransaction.getRrn())) {
                MessageFormat messageFormat = new MessageFormat("Transaction with RRN {0} and switch transaction with RRN {1} has same amount and different RRN are not match");
                logger.warn(messageFormat.format(new Object[]{atmTransaction.getRrn(), switchTransaction.getRrn()}));
                continue;
            }

            atmTransactionList.add(atmTransaction);
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
        ListIterator<MiddleEastBankSwitchTransaction> iterator = switchTransactions.listIterator(switchTransactions.indexOf(switchTransaction) + 1);

        if (!iterator.hasNext())
            return switchTransactions.size();

        MiddleEastBankSwitchTransaction next = iterator.next();

        if (next.getTerminalTransaction() == null)
            return getFollowSwitchTransactionIndex(next);

        return switchTransactions.indexOf(next);
    }
}
