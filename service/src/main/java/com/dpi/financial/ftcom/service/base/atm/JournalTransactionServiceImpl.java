package com.dpi.financial.ftcom.service.base.atm;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.atm.JournalTransactionService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.JournalTransactionDao;
import com.dpi.financial.ftcom.model.dao.atm.SwipeCardDao;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.JournalTransaction;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.model.type.OperationState;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.service.exception.OperationTerminatedException;
import com.dpi.financial.ftcom.service.exception.atm.journal.TerminalTransactionStateException;
import com.dpi.financial.ftcom.service.exception.atm.journal.UnexpectedLineException;
import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;
import com.dpi.financial.ftcom.utility.regex.RegexConstant;
import com.dpi.financial.ftcom.utility.regex.RegexMatches;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.transform.Source;
import java.io.*;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.MatchResult;

@Stateless
@Local(JournalTransactionService.class)
public class JournalTransactionServiceImpl extends GeneralServiceImpl<JournalTransaction>
        implements JournalTransactionService {

    @EJB
    private JournalFileService journalFileService;

    @EJB
    private JournalTransactionDao dao;

    @EJB
    SwipeCardDao swipeCardDao;

    @Override
    public GenericDao<JournalTransaction> getGenericDao() {
        return dao;
    }


    @Override
    public void prepareSwipeCard(String baseFolder, Terminal terminal) throws IOException {
        SwipeCard swipeCard = null;
        TerminalTransactionState state = TerminalTransactionState.TERMINAL_IDLE;
        TerminalTransactionState previousState = TerminalTransactionState.TERMINAL_IDLE;
        BufferedReader br = null;

        for (JournalFile journalFile : journalFileService.getJournalFileList(baseFolder, terminal)) {
            try {
                File file = new JournalPhysicalFile(baseFolder).getJournalFile(terminal.getLuno(), journalFile.getFileName());

                /*
                String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date cur = format.parse(fileNameWithOutExt);
                */

                br = new BufferedReader(new FileReader(file));

                long index = 0;
                String line;
                // String terminalId = file.getParent().substring(file.getParent().lastIndexOf("\\") + 1);


                while ((line = br.readLine()) != null) {

                    //http://www.ascii-code.com/
                    String endOfTransmission = String.valueOf(Character.toChars(4)); // ASCII control character EOT
                    if (line.contains(endOfTransmission))
                        line = line.replaceAll(endOfTransmission, "");

                    if (terminal.getLuno().equals("01003") &&
                            journalFile.getFileName().equals("20150704.jrn") &&
                            index + 1 == 440) {
                        System.out.println("I am here.");
                    }

                    if (state != TerminalTransactionState.INVALID_STATE)
                        previousState = state;

                    // state always depends on former line read and the current line is not state
                    switch (state) {
                        case TERMINAL_IDLE:
                            if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                                swipeCard = new SwipeCard();
                                // swipeCard.setTerminal(terminal);
                                swipeCard.setJournalFile(journalFile);
                                swipeCard.setLuno(journalFile.getLuno());
                                swipeCard.setSwipeDate(journalFile.getJournalDate());
                                swipeCard.setFileName(journalFile.getFileName());
                                swipeCard.setLineStart(index + 1);
                                swipeCard.setOperationState(OperationState.START);

                                DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                                Date transactionStartTime = format.parse(journalFile.getName() + " " + getTime(line));
                                swipeCard.setSwipeTime(transactionStartTime);
                            }
                            break;

                        case TRANSACTION_START:
                            if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                                throw new UnexpectedLineException(
                                        MessageFormat.format("Transaction start is not allowed here. See journal {0}/{1} line {2}",
                                                terminal.getLuno(), journalFile.getFileName(), index + 1)
                                );
                            } else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_DATA, line) != null) {
                                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_1_DATA, line) != null) {
                                    swipeCard.setTrack1Data(getTrackData(line));
                                }
                                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_2_DATA, line) != null) {
                                    swipeCard.setTrack2Data(getTrackData(line));
                                    swipeCard.setPan(getTrackData(line));
                                }
                                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_3_DATA, line) != null) {
                                    swipeCard.setTrack3Data(getTrackData(line));
                                }
                            } else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_PIN_ENTERED, line) != null ||
                                    RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null ||
                                    RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_END, line) != null) {
                                // end set by previous line number
                                swipeCard.setLineEnd(index);

                                if (!swipeCard.getOperationState().equals(OperationState.ERROR))
                                    swipeCard.setOperationState(OperationState.FINISHED);

                                System.out.println(
                                        MessageFormat.format("Swipe card: {0}/{1}/{2}/{3}",
                                                swipeCard.getLuno(), swipeCard.getJournalFile().getName(),
                                                swipeCard.getLineStart(), swipeCard.getLineEnd())
                                );

                                swipeCardDao.create(swipeCard);
                            } else {
                                swipeCard.setOperationState(OperationState.ERROR);
                            }

                            break;

                        /*
                        case TRACK_DATA:
                            break;
                        */

                        case PIN_ENTERED:
                            break;

                        case INFORMATION_ENTERED:
                            break;

                        case AMOUNT_ENTERED:
                            break;

                        case TRANSACTION_REQUEST:
                            break;

                        case TRANSACTION_REPLY:
                            break;

                        case CARD_TAKEN:
                            break;

                        case CASH_PRESENTED:
                            break;

                        case CASH_TAKEN:
                            break;

                        case TRANSACTION_END:
                            break;

                        // TODO: if state is not INVALID_STATE and come come to default add exception to inform developer to add new state as case
                        default: // INVALID_STATE
                            throw new TerminalTransactionStateException(
                                    MessageFormat.format("Reading this line from previous state {0} lead to unknown state. See journal {1}/{2}:{3}",
                                            previousState, terminal.getLuno(), journalFile.getFileName(), index)
                            );
                            // break;
                    }

                    state = getNextState(state, line);



                            /*
                            throw new UnexpectedLineException(
                                    MessageFormat.format("Only track data is allowed here. See journal {0}/{1} line {2}",
                                            terminal.getLuno(), journalFile.getFileName(), index + 1)
                            );
                            */

                        /*
                        List<ITransaction> sequentialTranactions = prepareSequentialBlock(atmTransactionHdr.getLineList());

                        ITransaction general = sequentialTranactions.get(0);
                        if (general instanceof GeneralTransaction) {
                            String cardNumber = general.entry().get(ATMConstant.ATM_STATE_TRACK_2_DATA);
                            atmTransactionHdr.setCardNumber(cardNumber);
                        }

                        atmTransactionHdr = atmTransactionService.saveAtmTransactionHdr(atmTransactionHdr);

                        atmTransactionHdr.setTransactionList(sequentialTranactions);
                        List<AtmTransaction> atmTransactionList = populateAtmTransactionList(atmTransactionHdr);

                        for (AtmTransaction atmTransaction : atmTransactionList) {
                            atmTransactionService.saveCashWithdrawal(atmTransaction);
                        }

                        list = new ArrayList<String>();
                        */


                    index++;
                }
            } catch (ParseException | MultipleMatchException | IOException e) {
                e.printStackTrace();
                throw new OperationTerminatedException(
                        MessageFormat.format("Operation failed while preparing swipe card for terminal {0}",
                                terminal.getLuno())
                );
            } finally {
                if (br != null)
                    br.close();
                br = null;
            }
        }
    }

    private TerminalTransactionState getNextState(TerminalTransactionState currentState, String line) throws MultipleMatchException {
        TerminalTransactionState nextState = TerminalTransactionState.INVALID_STATE;

        if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TERMINAL_MESSAGE, line) != null ||
                line.equals("/~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\") ||
                line.equals("\\______________________________________/"))
            return currentState;

        /*
        if (db.getNextState(cur, pattern) != null)
            nextState = db.getNextState(cur, pattern);
        */
        switch (currentState) {
            case TERMINAL_IDLE:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else
                    nextState = currentState;
                break;

            case TRANSACTION_START:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_DATA, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                break;

            case PIN_ENTERED:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_INFORMATION_ENTERED, line) != null)
                    nextState = TerminalTransactionState.INFORMATION_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.AMOUNT_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_APPLICATION_STARTED, line) != null)
                    nextState = TerminalTransactionState.TERMINAL_IDLE;
                break;

            case INFORMATION_ENTERED:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.AMOUNT_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_INFORMATION_ENTERED, line) != null)
                    nextState = TerminalTransactionState.INFORMATION_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                break;

            case AMOUNT_ENTERED:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.AMOUNT_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                break;

            case TRANSACTION_REQUEST:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_REPLY, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REPLY;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                break;

            case TRANSACTION_REPLY:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case CARD_TAKEN:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CASH_PRESENTED, line) != null)
                    nextState = TerminalTransactionState.CASH_PRESENTED;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case CASH_PRESENTED:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CASH_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CASH_TAKEN;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case CASH_TAKEN:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_CASH_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CASH_TAKEN;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case TRANSACTION_END:
                if (RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else
                    nextState = TerminalTransactionState.TERMINAL_IDLE;
                break;
        }

        return nextState;
    }


    private String getTime(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TERMINAL_TRANSACTION_TIME, line);
        if (match != null)
            value = line.substring(match.start(), match.end());

        return value.trim();
    }

    protected String getTrackData(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_1_DATA, line);
        if (match != null)
            value = line.substring(match.end());

        match = RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_2_DATA, line);
        if (match != null)
            value = line.substring(match.end());

        match = RegexMatches.getSingleResult(RegexConstant.ATM_REGEX_TRACK_3_DATA, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    /*
    @Override
    public List<JournalFile> prepareAtmTransactions(String baseFolder, Terminal terminal) {
        AtmTransactionService atmTransactionService = new AtmTransactionService();
        SwitchTransactionService switchTransactionService = new SwitchTransactionService();

        List<JournalFile> journalFiles = findAll();


        List<String> list = new ArrayList<String>();
        boolean bTransaction = false;
        String luno = null;
        String fileName = null;
        int start = 0;
        int endLineNumber = 0;
        JournalBean journalBean = new JournalBean();

        BufferedReader br = null;
        try {

            AtmTransactionHdr lastAtmTransactionHdr = atmTransactionService.getLastTransactionHdr(terminalFolder.getName());
            if (lastAtmTransactionHdr != null) {
                switchTransactionService.detachSwitchTransaction(lastAtmTransactionHdr.getTerminalId(), lastAtmTransactionHdr.getFileName(), lastAtmTransactionHdr.getLineStart());
                atmTransactionService.deleteAtmTransactionHdr(lastAtmTransactionHdr);
            }

            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date last = new Date(Long.MIN_VALUE);
            if (lastAtmTransactionHdr != null)
                last = format.parse(lastAtmTransactionHdr.getFileName());

            for (File file : journalBean.getOrderedJournalFiles(terminalFolder)) {

                String terminalId = file.getParent().substring(file.getParent().lastIndexOf("\\") + 1);
                String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                Date cur = format.parse(fileNameWithOutExt);

                if (cur.before(last))
                    continue;

                br = new BufferedReader(new FileReader(file));

                int index = 0;
                String line;

                if (cur.equals(last)) {
                    while (index + 1 < lastAtmTransactionHdr.getLineStart()) {
                        line = br.readLine();
                        index++;
                    }
                }

                while ((line = br.readLine()) != null) {
                    if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                        list = new ArrayList<String>();
                        bTransaction = true;
                        luno = terminalId;
                        fileName = fileNameWithOutExt;
                        start = index + 1;
                    }

                    if (bTransaction)
                        list.add(line);

                    if (bTransaction && RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null) {
                        AtmTransactionHdr atmTransactionHdr = new AtmTransactionHdr();
                        atmTransactionHdr.setTerminalId(luno);
                        atmTransactionHdr.setFileName(fileName);
                        atmTransactionHdr.setLineStart(start);
                        atmTransactionHdr.setLineEnd(index + 1);
                        atmTransactionHdr.setLineList(list);

                        System.out.println("journal : " + atmTransactionHdr.getTerminalId() + "/" + atmTransactionHdr.getFileName() + "/" + atmTransactionHdr.getLineStart() + "/"
                                + atmTransactionHdr.getLineEnd());

                        List<ITransaction> sequentialTranactions = prepareSequentialBlock(atmTransactionHdr.getLineList());

                        ITransaction general = sequentialTranactions.get(0);
                        if (general instanceof GeneralTransaction) {
                            String cardNumber = general.entry().get(ATMConstant.ATM_STATE_TRACK_2_DATA);
                            atmTransactionHdr.setCardNumber(cardNumber);
                        }

                        atmTransactionHdr = atmTransactionService.saveAtmTransactionHdr(atmTransactionHdr);

                        atmTransactionHdr.setTransactionList(sequentialTranactions);
                        List<AtmTransaction> atmTransactionList = populateAtmTransactionList(atmTransactionHdr);

                        for (AtmTransaction atmTransaction : atmTransactionList) {
                            atmTransactionService.saveCashWithdrawal(atmTransaction);
                        }

                        bTransaction = false;
                        list = new ArrayList<String>();
                    }

                    index++;
                }
            }
        } catch (IOException | MultipleMatchException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
        }
    }
    */


}
