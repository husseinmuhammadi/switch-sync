package com.dpi.financial.ftcom.service.base.atm;

import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.atm.TerminalTransactionService;
import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.SwipeCardDao;
import com.dpi.financial.ftcom.model.dao.atm.TerminalTransactionDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.journal.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.dpi.financial.ftcom.model.to.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.type.OperationState;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import com.dpi.financial.ftcom.service.exception.OperationTerminatedException;
import com.dpi.financial.ftcom.service.exception.atm.journal.OperationNotAllowedException;
import com.dpi.financial.ftcom.service.exception.atm.journal.StateProcessNotDefinedException;
import com.dpi.financial.ftcom.service.exception.atm.journal.TerminalTransactionStateException;
import com.dpi.financial.ftcom.utility.atm.journal.ATMConstant;
import com.dpi.financial.ftcom.utility.atm.journal.JournalContent;
import com.dpi.financial.ftcom.utility.convert.Convert;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;
import com.dpi.financial.ftcom.utility.regex.RegexMatches;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;

@Stateless
@Local(TerminalTransactionService.class)
public class TerminalTransactionServiceImpl extends GeneralServiceImpl<TerminalTransaction>
        implements TerminalTransactionService {

    @EJB
    private JournalFileService journalFileService;

    @EJB
    private OperationCodeService operationCodeService;

    @EJB
    private TerminalTransactionDao dao;

    @EJB
    SwipeCardDao swipeCardDao;

    @Override
    public GenericDao<TerminalTransaction> getGenericDao() {
        return dao;
    }


    @Override
    public void prepareAtmTransactions(String baseFolder, Terminal terminal, Date journalDateFrom, Date journalDateTo) throws IOException {
        TerminalTransactionState state = TerminalTransactionState.IDLE;
        TerminalTransactionState previousState = TerminalTransactionState.IDLE;

        SwipeCard swipeCard = null;
        TerminalTransaction transaction = null;

        Map<String, OperationCode> operationCodeMap = new HashMap<String, OperationCode>();
        operationCodeService.findAllByEffectiveDate(DateUtil.getCurrentDate()).forEach(item -> {
            operationCodeMap.put(item.getOperationCodeBuffer(), item);
        });

        BufferedReader br = null;

        StringBuffer allInformationEntered = new StringBuffer(200);
        StringBuffer allAmountEntered = new StringBuffer(200);
        BigDecimal amountEntered = null;

        for (JournalFile journalFile : journalFileService.getJournalFileList(baseFolder, terminal, journalDateFrom, journalDateTo)) {
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
                            journalFile.getFileName().equals("20160102.jrn") &&
                            index + 1 == 811) {
                        System.out.println("I am here.");
                    }

                    if (state != TerminalTransactionState.INVALID)
                        previousState = state;

                    // state always depends on former line read and the current line is not state

                    switch (state) {
                        case IDLE:
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                                swipeCard = createSwipeCard(journalFile, index, line);
                                transaction = null;
                            }
                            break;

                        case TRANSACTION_START:
                            /* Do not remove this comment
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                                throw new UnexpectedLineException(
                                        MessageFormat.format("Transaction start is not allowed here. See journal {0}/{1} line {2}",
                                                terminal.getLuno(), journalFile.getFileName(), index + 1)
                                );
                            }
                            */

                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_DATA, line) != null) {
                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_1_DATA, line) != null) {
                                    swipeCard.setTrack1Data(JournalContent.getTrackData(line));
                                }
                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_2_DATA, line) != null) {
                                    swipeCard.setTrack2Data(JournalContent.getTrackData(line));
                                    swipeCard.setPan(JournalContent.getTrackData(line));
                                }
                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_3_DATA, line) != null) {
                                    swipeCard.setTrack3Data(JournalContent.getTrackData(line));
                                }
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null
                                    || RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null
                                    || RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null
                                    || RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null) {
                                saveSwipeCard(swipeCard, index);

                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null) {
                                    allInformationEntered.setLength(0);
                                    allAmountEntered.setLength(0);
                                    amountEntered = null;

                                    if (transaction != null) {
                                        throw new OperationNotAllowedException(
                                                MessageFormat.format("Unsaved transaction not allowed in state {0} on {1}/{2}:{3}",
                                                        state, journalFile.getLuno(), journalFile.getName(), index + 1)
                                        );
                                    }
                                }
                            } else {
                                swipeCard.setOperationState(OperationState.ERROR);
                            }

                            break;

                        case PIN_ENTERED:
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_INFORMATION_ENTERED, line) != null) {
                                allInformationEntered.append("/").append(JournalContent.getInformationEntered(line));
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null) {
                                allAmountEntered.append("/").append(JournalContent.getAmountEntered(line));
                                if (!StringUtils.isEmpty(JournalContent.getAmountEntered(line)))
                                    amountEntered = Convert.parseDecimal(JournalContent.getAmountEntered(line));
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null) {
                                transaction = new TerminalTransaction();

                                if (allInformationEntered.length() > 0)
                                    transaction.setAllInformationEntered(allInformationEntered.toString());

                                if (allAmountEntered.length() > 0)
                                    transaction.setAllAmountEntered(allAmountEntered.toString());

                                if (amountEntered!=null)
                                    transaction.setAmountEntered(amountEntered);

                                transaction.setSwipeCard(swipeCard);
                                transaction.setLuno(journalFile.getLuno());
                                transaction.setFileName(journalFile.getFileName());
                                transaction.setLineStart(index + 1);
                                transaction.setOperationState(OperationState.START);
                                transaction.setTransactionDate(journalFile.getJournalDate());
                                DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                                Date requestTime = format.parse(journalFile.getName() + " " + JournalContent.getTime(line));
                                transaction.setTransactionTime(requestTime);
                                transaction.setTerminalRequestTime(requestTime);
                                transaction.setTransactionRequest(JournalContent.getTransactionRequest(line));

                                OperationCode operationCode = operationCodeMap.get(transaction.getTransactionRequest());
                                transaction.setProcessingCode(operationCode.getProcessingCode());
                                if (operationCode.getAmount() != null)
                                    transaction.setFastCashAmount(operationCode.getAmount());

                                /*
                                put(getTransactionTypeProp(), getTransactionType(transactionRequest));
                                put(getTransactionAmountProp(), String.valueOf(getTransactionAmount(transactionRequest)));
                                put(getTerminalTransactionTimeProp(), getTerminalTransactionTime(line));
                                put(getLineIndexProp(), String.valueOf(iterator.previousIndex()));
                                line = readAtmStatusMessage();
                                */
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null) {
                                if (transaction != null) {
                                    saveTransaction(transaction, index);
                                    transaction = null;

                                    allInformationEntered.setLength(0);
                                    allAmountEntered.setLength(0);
                                    amountEntered = null;
                                }
                            }
                            break;

                        case TRANSACTION_REQUEST:
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null) {
                                saveTransaction(transaction, index);
                                transaction = null;

                                allInformationEntered.setLength(0);
                                allAmountEntered.setLength(0);
                                amountEntered = null;
                                break;
                            }

                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REPLY, line) != null) {
                                transaction.setTransactionReply(JournalContent.getTransactionReply(line));

                                DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                                Date replyTime = format.parse(journalFile.getName() + " " + JournalContent.getTime(line));
                                transaction.setTerminalReplyTime(replyTime);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_REQUEST, line) != null) {
                                transaction.setCashRequest(JournalContent.getCashRequest(line));
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH, line) != null) {
                                transaction.setCash(JournalContent.getCash(line));
                                transaction.setCashNoteFromCassetteOne(JournalContent.getCashNoteNoFromCassetteOne(JournalContent.getCash(line)));
                                transaction.setCashNoteFromCassetteTwo(JournalContent.getCashNoteNoFromCassetteTwo(JournalContent.getCash(line)));
                                transaction.setCashNoteFromCassetteThree(JournalContent.getCashNoteNoFromCassetteThree(JournalContent.getCash(line)));
                                transaction.setCashNoteFromCassetteFour(JournalContent.getCashNoteNoFromCassetteFour(JournalContent.getCash(line)));
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null) {
                                transaction.setCardTaken(YesNoType.Yes);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_PRESENTED, line) != null) {
                                transaction.setCashPresented(YesNoType.Yes);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_TAKEN, line) != null) {
                                transaction.setCashTaken(YesNoType.Yes);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_RETRACTED, line) != null) {
                                transaction.setCashRetracted(YesNoType.Yes);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_RETAINED, line) != null) {
                                transaction.setCardRetained(YesNoType.Yes);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_JAMMED, line) != null) {
                                transaction.setCardJammed(YesNoType.Yes);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_COMMUNICATION_OFFLINE, line) != null) {
                                transaction.setCommunicationOffline(Boolean.TRUE);
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_COMMUNICATION_ERROR, line) != null) {
                                transaction.setCommunicationError(Boolean.TRUE);
                            }

                            // Receipt
                            if (transaction.getProcessingCode() == ProcessingCode.CASH_WITHDRAWAL
                                    && RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_WITHDRAWAL, line) != null)
                                transaction.setReceiptTitle(JournalContent.getCashWithdrawal(line));

                            if (transaction.getProcessingCode() == ProcessingCode.CASH_WITHDRAWAL
                                    && transaction.getReceiptTitle() != null) {

                                MatchResult match;

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_WITHDRAWAL, line);
                                if (match != null)
                                    transaction.setCashWithdrawal(YesNoType.Yes);

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SWITCH_DATE_TIME_LUNO, line);
                                if (match != null) {
                                    transaction.setSwitchDateTime(JournalContent.getSwitchDateTime(line));
                                    transaction.setReceiptTreminalId(JournalContent.getTreminalId(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_NO, line);
                                if (match != null) {
                                    transaction.setReceiptCardNumber(JournalContent.getCardNo(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RRN, line);
                                if (match != null) {
                                    transaction.setRrn(JournalContent.getRrn(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_STAN, line);
                                if (match != null) {
                                    transaction.setStan(JournalContent.getStan(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT, line);
                                if (match != null) {
                                    transaction.setAmount(JournalContent.getCashAmount(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RESPONSE_CODE, line);
                                if (match != null) {
                                    transaction.setResponseCode(JournalContent.getResponseCode(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RESPONSE, line);
                                if (match != null) {
                                    transaction.setResponse(JournalContent.getResponse(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SUCCESSFUL, line);
                                if (match != null) {
                                    transaction.setSuccessful(Boolean.TRUE);
                                    transaction.setSuccess(JournalContent.getSuccessMessage(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_UNSUCCESSFUL, line);
                                if (match != null) {
                                    transaction.setSuccessful(Boolean.FALSE);
                                    transaction.setSuccess(JournalContent.getSuccessMessage(line));
                                }
                            }

                            break;

                        case TRANSACTION_END:
                            if (transaction != null) {
                                saveTransaction(transaction, index);
                                transaction = null;

                                allInformationEntered.setLength(0);
                                allAmountEntered.setLength(0);
                                amountEntered = null;
                            }
                            swipeCard = null;
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                                swipeCard = createSwipeCard(journalFile, index, line);
                                transaction = null;
                            }
                            break;

                        default: // INVALID_STATE
                            if (state != TerminalTransactionState.INVALID)
                                throw new StateProcessNotDefinedException(
                                        MessageFormat.format("State {0} not defined for processing. Please add case for this state.", state)
                                );

                            throw new TerminalTransactionStateException(
                                    MessageFormat.format("Reading this line from previous state {0} lead to unknown state. See journal {1}/{2}:{3}",
                                            previousState, terminal.getLuno(), journalFile.getFileName(), index)
                            );
                            // break;
                    }

                    state = getNextState(state, line);

                    /*
                    switch (state) {
                        case TERMINAL_IDLE:
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
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
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null) {
                                throw new UnexpectedLineException(
                                        MessageFormat.format("Transaction start is not allowed here. See journal {0}/{1} line {2}",
                                                terminal.getLuno(), journalFile.getFileName(), index + 1)
                                );
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_DATA, line) != null) {
                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_1_DATA, line) != null) {
                                    swipeCard.setTrack1Data(getTrackData(line));
                                }
                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_2_DATA, line) != null) {
                                    swipeCard.setTrack2Data(getTrackData(line));
                                    swipeCard.setPan(getTrackData(line));
                                }
                                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_3_DATA, line) != null) {
                                    swipeCard.setTrack3Data(getTrackData(line));
                                }
                            } else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null ||
                                    RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null ||
                                    RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null) {
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

                        default: // INVALID_STATE
                            throw new TerminalTransactionStateException(
                                    MessageFormat.format("Reading this line from previous state {0} lead to unknown state. See journal {1}/{2}:{3}",
                                            previousState, terminal.getLuno(), journalFile.getFileName(), index)
                            );
                            // break;
                    }
                    */


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

    @Override
    public void synchronizeAtmTransactions(Date journalDateFrom, Date journalDateTo) {

    }

    private SwipeCard createSwipeCard(JournalFile journalFile, long index, String line) throws MultipleMatchException, ParseException {
        SwipeCard swipeCard = new SwipeCard();
        // swipeCard.setTerminal(terminal);

        swipeCard.setJournalFile(journalFile);
        swipeCard.setLuno(journalFile.getLuno());
        swipeCard.setSwipeDate(journalFile.getJournalDate());
        swipeCard.setFileName(journalFile.getFileName());
        swipeCard.setLineStart(index + 1);
        swipeCard.setOperationState(OperationState.START);

        DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date transactionStartTime = format.parse(journalFile.getName() + " " + JournalContent.getTime(line));
        swipeCard.setSwipeTime(transactionStartTime);
        return swipeCard;
    }

    private TerminalTransaction saveTransaction(TerminalTransaction transaction, Long index) {
        transaction.setLineEnd(index);

        if (!transaction.getOperationState().equals(OperationState.ERROR))
            transaction.setOperationState(OperationState.FINISHED);

        System.out.println(
                MessageFormat.format("Terminal transaction: {0}/{1}/{2}/{3}",
                        transaction.getLuno(), transaction.getSwipeCard().getJournalFile().getName(),
                        transaction.getLineStart(), transaction.getLineEnd())
        );

        return dao.create(transaction);
    }

    private void saveSwipeCard(SwipeCard swipeCard, Long index) {
        // end set by previous line number
        swipeCard.setLineEnd(index);

        if (!swipeCard.getOperationState().equals(OperationState.ERROR))
            swipeCard.setOperationState(OperationState.FINISHED);

        System.out.println(
                MessageFormat.format("          Swipe card: {0}/{1}/{2}/{3}",
                        swipeCard.getLuno(), swipeCard.getJournalFile().getName(),
                        swipeCard.getLineStart(), swipeCard.getLineEnd())
        );

        swipeCardDao.create(swipeCard);
    }

    private TerminalTransactionState getNextState(TerminalTransactionState currentState, String line) throws MultipleMatchException {
        TerminalTransactionState nextState = TerminalTransactionState.INVALID;

        if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_IGNORE, line) != null)
            return currentState;

        if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TERMINAL_MESSAGE, line) != null)
            return currentState;

        /*
        if (db.getNextState(cur, pattern) != null)
            nextState = db.getNextState(cur, pattern);
        */

        switch (currentState) {
            case IDLE:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else { // EXCEPTION
                    if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) == null
                            || RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) == null
                            || RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) == null
                            || RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) == null)
                        nextState = currentState;
                }
                break;

            case TRANSACTION_START:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_DATA, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                break;

            case PIN_ENTERED:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_INFORMATION_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REPLY, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_APPLICATION_STARTED, line) != null)
                    nextState = TerminalTransactionState.IDLE;
                break;

            case TRANSACTION_REQUEST:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else { // EXCEPTION
                    // if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) == null)
                    nextState = currentState;
                }
                break;

            case TRANSACTION_END:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else { // EXCEPTION
                    nextState = TerminalTransactionState.IDLE;
                }
                break;
        }

        /*
        switch (currentState) {
            case TERMINAL_IDLE:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else
                    nextState = currentState;
                break;

            case TRANSACTION_START:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_DATA, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                break;

            case PIN_ENTERED:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_INFORMATION_ENTERED, line) != null)
                    nextState = TerminalTransactionState.INFORMATION_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.AMOUNT_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_APPLICATION_STARTED, line) != null)
                    nextState = TerminalTransactionState.TERMINAL_IDLE;
                break;

            case INFORMATION_ENTERED:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.AMOUNT_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_INFORMATION_ENTERED, line) != null)
                    nextState = TerminalTransactionState.INFORMATION_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                break;

            case AMOUNT_ENTERED:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REQUEST;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED, line) != null)
                    nextState = TerminalTransactionState.AMOUNT_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line) != null)
                    nextState = TerminalTransactionState.PIN_ENTERED;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                break;

            case TRANSACTION_REQUEST:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REPLY, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_REPLY;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                break;

            case TRANSACTION_REPLY:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CARD_TAKEN;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case CARD_TAKEN:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_PRESENTED, line) != null)
                    nextState = TerminalTransactionState.CASH_PRESENTED;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case CASH_PRESENTED:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CASH_TAKEN;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case CASH_TAKEN:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_END, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_END;
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_TAKEN, line) != null)
                    nextState = TerminalTransactionState.CASH_TAKEN;
                else
                    nextState = currentState; // CASH WITHDRAWAL
                break;

            case TRANSACTION_END:
                if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
                else
                    nextState = TerminalTransactionState.TERMINAL_IDLE;
                break;
        }
        */

        return nextState;
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
