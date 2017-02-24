package com.dpi.financial.ftcom.web.controller.base.meb.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalContentService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.meb.atm.TerminalOperationStateService;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.SwipeCardService;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.api.base.meb.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.api.exception.OperationTerminatedException;
import com.dpi.financial.ftcom.api.exception.UnexpectedLineException;
import com.dpi.financial.ftcom.api.exception.atm.ndc.OperationCodeNotFoundException;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.ndc.OperationCode;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalContent;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.TerminalOperationState;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.type.OperationState;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.model.type.terminal.TerminalMessageType;
import com.dpi.financial.ftcom.model.type.terminal.TerminalOperation;
import com.dpi.financial.ftcom.model.type.terminal.TerminalOperationType;
import com.dpi.financial.ftcom.model.type.terminal.transaction.TerminalTransactionState;
import com.dpi.financial.ftcom.model.type.terminal.transaction.UserAction;
import com.dpi.financial.ftcom.utility.atm.journal.ATMConstant;
import com.dpi.financial.ftcom.utility.convert.Convert;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;
import com.dpi.financial.ftcom.utility.regex.RegexMatches;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import com.dpi.financial.ftcom.web.controller.conf.AtmConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.MatchResult;

@Named("middleEastBankJournalFileManager")
@ViewScoped
public class JournalFileManager extends ControllerManagerBase<JournalFile> implements Serializable {
    private static final long serialVersionUID = -3032343937325405624L;
    Logger logger = LoggerFactory.getLogger(JournalFileManager.class);

    @EJB
    private JournalFileService service;

    @EJB
    private JournalContentService journalContentService;

    @EJB
    private TerminalService terminalService;

    @EJB
    TerminalTransactionService terminalTransactionService;

    @EJB
    private JournalFileService journalFileService;

    @EJB
    SwitchTransactionService switchTransactionService;

    @EJB
    TerminalOperationStateService terminalOperationStateService;

    @EJB
    private OperationCodeService operationCodeService;

    @EJB
    private SwipeCardService swipeCardService;

    @Inject
    private AtmConfiguration configuration;

    private Terminal terminal;
    // private List<JournalFile> journalFileList;
    Date journalDateFrom;
    Date journalDateTo;

    public JournalFileManager() {
        super(JournalFile.class);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        /*
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        */
        journalDateFrom = cal.getTime();

        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        journalDateTo = cal.getTime();

        terminal = new Terminal();
    }

    @Override
    public GeneralServiceApi<JournalFile> getGeneralServiceApi() {
        return service;
    }

    @Override
    public void onLoad() {
        try {
            String luno = terminal.getLuno();
            if (luno == null)
                return;

            // String path = configuration.getJournalPath();
            setJournalFileList(service.findAll(terminalService.findByLuno(luno)));
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public void lunoValueChange(AjaxBehaviorEvent event) throws AbortProcessingException {
        String luno = ((UIInput) event.getComponent()).getValue().toString();
        logger.info(MessageFormat.format("Get journal files for logical unit number: {0}/{1}", luno, getTerminal().getLuno()));
        try {
            Terminal terminal = terminalService.findByLuno(luno);
            setJournalFileList(service.findAll(terminal));
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public void reload(AjaxBehaviorEvent event) throws AbortProcessingException {
        String luno = terminal.getLuno();
        if (luno == null)
            return;

        Terminal terminal = terminalService.findByLuno(luno);

        logger.info(MessageFormat.format("Find all physical journal file for {0} in path {1}", luno, configuration.getJournalPath()));
        List<JournalFile> journalFiles = service.findAll(terminal);

        JournalPhysicalFile journalPhysicalFile = new JournalPhysicalFile(configuration.getJournalPath());
        try {
            List<File> journalPhysicalFiles = journalPhysicalFile.getOrderedJournalFiles(terminal.getLuno());
            for (File file : journalPhysicalFiles) {
                JournalFile journal = new JournalFile();

                journal.setLuno(terminal.getLuno());
                journal.setName(FilenameUtils.getBaseName(file.getName()));
                journal.setFileName(file.getName());
                journal.setTerminal(terminal);
                updateFileInfo(journal, file);

                // journal.setState(JournalFileState.ENTRY);

                // http://stackoverflow.com/questions/13138990/how-to-search-in-a-list-of-java-object
                /*
                List<JournalFile> result = journalFiles.stream()
                        .filter(item -> item.getFileName().equals(journal.getFileName()))
                        .collect(Collectors.toList());
                */
                Optional<JournalFile> any = journalFiles.stream()
                        .filter(item -> item.getFileName().equals(journal.getFileName()))
                        .findAny();

                if (any.isPresent()) {
                    JournalFile journalFile = any.get();
                    logger.info(MessageFormat.format("Journal {0}/{1} found.", journalFile.getLuno(), journalFile.getFileName()));
                    logger.info(MessageFormat.format("{0}/{1}", journalFile.getMd5(), journal.getMd5()));
                    if (!journalFile.getMd5().equals(journal.getMd5())) {
                        logger.info(MessageFormat.format("Journal {0}/{1} content is different.", journalFile.getLuno(), journalFile.getFileName()));
                        updateFileInfo(journalFile, file);
                        // journalFile.setState(JournalFileState.ENTRY);
                        service.update(journalFile);
                    }
                } else {
                    JournalFile journalFile = service.create(journal);
                    logger.info(MessageFormat.format("Journal {0}/{1} added.", journalFile.getLuno(), journalFile.getFileName()));
                }
            }

            setJournalFileList(service.findAll(terminal));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private JournalFile updateFileInfo(JournalFile journal, File file) {
        try {
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
            journal.setJournalDate(format.parse(fileNameWithOutExt));

            Path path = Paths.get(file.getCanonicalPath());
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

            journal.setCreationTime(new Date(attr.creationTime().toMillis()));
            journal.setLastAccessTime(new Date(attr.lastAccessTime().toMillis()));
            journal.setLastModifiedTime(new Date(attr.lastModifiedTime().toMillis()));

            journal.setDirectory(attr.isDirectory());
            journal.setOther(attr.isOther());
            journal.setRegularFile(attr.isRegularFile());
            journal.setSymbolicLink(attr.isSymbolicLink());

            journal.setSize(attr.size());

            journal.setDigest(getDigest(path));
            journal.setMd5(encodeToMd5(file.getAbsolutePath()));

        } catch (ParseException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return journal;
    }

    /**
     * see whats going on calcMD5
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private byte[] getDigest(Path file) throws IOException, NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(file);
             DigestInputStream dis = new DigestInputStream(is, algorithm)) {
            /* Read decorated stream (dis) to EOF as normal... */
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) != -1)
                ;
        }
        byte[] digest = algorithm.digest();
        // System.out.println("Digest: " + digest);
        return digest;
    }

    public String encodeToMd5(String filePath) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(filePath);

        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();

        // convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());
        return sb.toString();
    }

    public void transfer(AjaxBehaviorEvent event) throws AbortProcessingException {
        String luno = terminal.getLuno();
        if (luno == null)
            return;

        Terminal terminal = terminalService.findByLuno(luno);

        logger.info(MessageFormat.format("Find all physical journal file for {0} in path {1}", luno, configuration.getJournalPath()));
        List<JournalFile> journalFiles = service.findAll(terminal);

        BufferedReader br = null;
        List<JournalContent> journalContents = new ArrayList<>();

        for (JournalFile journalFile : journalFiles) {
            // if (journalFile.getState() != JournalFileState.ENTRY) continue;

            try {
                File file = new JournalPhysicalFile(configuration.getJournalPath()).getJournalFile(terminal.getLuno(), journalFile.getFileName());

                /*
                String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date cur = format.parse(fileNameWithOutExt);
                */

                br = new BufferedReader(new FileReader(file));

                journalContents.clear();

                int index = 0;
                String line;
                // String terminalId = file.getParent().substring(file.getParent().lastIndexOf("\\") + 1);


                while ((line = br.readLine()) != null) {
                    JournalContent content = new JournalContent();
                    content.setJournalFile(journalFile);
                    content.setLuno(journalFile.getLuno());
                    content.setName(journalFile.getName());
                    content.setFileName(journalFile.getFileName());
                    content.setJournalDate(journalFile.getJournalDate());
                    content.setLineNumber(index + 1);
                    content.setLine(line);

                    // journalFile.getContents().add(content);
                    journalContents.add(content);

                    index++;
                }

                journalContentService.saveContents(journalContents);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param event
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
     * <li>Prepare ATM transactions based on journal content</li>
     */
    public void prepareAtmTransactions(AjaxBehaviorEvent event) {
        String luno = terminal.getLuno();
        try {
            Terminal terminal = terminalService.findByLuno(luno);
            prepareAtmTransactions(terminal);
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    private void prepareAtmTransactions(Terminal terminal) throws IOException {
        TerminalTransactionState state = TerminalTransactionState.IDLE;
        // TerminalTransactionState previousState = TerminalTransactionState.IDLE;

        SwipeCard swipeCard = null;
        TerminalTransaction transaction = null;

        Map<String, OperationCode> operationCodeMap = new HashMap<String, OperationCode>();
        operationCodeService.findAllByEffectiveDate(DateUtil.getCurrentDate()).forEach(item -> {
            operationCodeMap.put(item.getOperationCodeBuffer(), item);
        });

        BufferedReader br = null;

        StringBuilder allInformationEntered = new StringBuilder(200);
        StringBuilder allAmountEntered = new StringBuilder(200);
        BigDecimal amountEntered = null;

        // Add 1 day, may be last transaction is on the next journal file
        // journalDateTo = DateUtil.addDays(journalDateTo, 1);
        List<JournalFile> journalFiles = journalFileService.findAll(terminal);

        /*
        JournalFile last = null;
        if (journalFiles.size() > 1)
            last = journalFiles.get(journalFiles.size() - 1);
        */

        // JournalFile previousJournalFile = null;

        for (JournalFile journalFile : journalFiles) {
            logger.info("Journal date {}", journalFile.getJournalDate());
            logger.info("Terminal last date {}", terminal.getLastJournalDate());

            if (journalFile.getJournalDate().before(terminal.getLastJournalDate())) {
                logger.info("File {} ignored", journalFile.getFileName());
                continue;
            }

            // if (journalFile.getState() == JournalFileState.PREPARED || journalFile.getState() == JournalFileState.RECONCILED) continue;

            try {
                File file = new File(configuration.getJournalPath() + "\\" + terminal.getLuno() + "\\" + journalFile.getFileName());

                /*
                String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date cur = format.parse(fileNameWithOutExt);
                */

                br = new BufferedReader(new FileReader(file));

                long index = 0;
                String line;
                // String terminalId = file.getParent().substring(file.getParent().lastIndexOf("\\") + 1);

                if (journalFile.getJournalDate().equals(terminal.getLastJournalDate()))
                    while (index < terminal.getLastJournalLineNumber()) {
                        br.readLine();
                        index++;
                    }

                while ((line = br.readLine()) != null) {
                    //http://www.ascii-code.com/
                    String endOfTransmission = String.valueOf(Character.toChars(4)); // ASCII control character EOT
                    if (line.contains(endOfTransmission))
                        line = line.replaceAll(endOfTransmission, "");

                    // state always depends on former line read and the current line is not state
                    // if (state != TerminalTransactionState.INVALID) previousState = state;

                    TerminalOperation terminalOperation = TerminalOperation.createInstance(line);
                    TerminalOperationState operationState = getNextOperationState(state, terminalOperation.getTerminalOperationType());

                    if (operationState.getFollowingState() == TerminalTransactionState.NOT_DEFINED) {
                        throw new UnexpectedLineException(MessageFormat.format("See journal {0}/{1} state:{2} line: {3}", terminal.getLuno(), journalFile.getFileName(), state, index + 1));
                    }

                    state = operationState.getFollowingState();
                    UserAction action = operationState.getUserAction();

                    switch (action) {
                        case TRANSACTION_START:
                            // Save unsaved swipe card and transaction
                            if (swipeCard != null) {

                                ////////////////////////////////////////////////////////////
                                // Rare case for unexpected transaction start
                                // Line End should be always have value on saving transaction
                                // Its value is set on transaction end
                                // and operation state should set to finished
                                ////////////////////////////////////////////////////////////
                                if (swipeCard.getOperationState() != OperationState.FINISHED)
                                    swipeCard.setOperationState(OperationState.ERROR);

                                if (swipeCard.getLineEnd() == null)
                                    swipeCard.setLineEnd(swipeCard.getLineStart());
                                /////////////////////////////////////////////////////////////

                                if (transaction != null)
                                    swipeCard.getTerminalTransactions().add(transaction);
                                terminalTransactionService.saveSwipeCardAndTransactions(swipeCard);
                                swipeCard = null;
                            }

                            transaction = null;

                            swipeCard = new SwipeCard();
                            // swipeCard.setTerminal(terminal);
                            swipeCard.setJournalFile(journalFile);
                            swipeCard.setLuno(journalFile.getLuno());
                            swipeCard.setSwipeDate(journalFile.getJournalDate());
                            swipeCard.setFileName(journalFile.getFileName());
                            swipeCard.setLineStart(index + 1);
                            swipeCard.setOperationState(OperationState.START);

                            DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                            Date transactionStartTime = format.parse(journalFile.getName() + " " + com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTime(line));
                            swipeCard.setSwipeTime(transactionStartTime);

                            swipeCard.setTerminalTransactions(new ArrayList<>());

                            break;

                        case TRACK_DATA:
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_1_DATA, line) != null) {
                                swipeCard.setTrack1Data(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTrackData(line));
                            }
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_2_DATA, line) != null) {
                                swipeCard.setTrack2Data(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTrackData(line));
                                swipeCard.setPan(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTrackData(line));
                            }
                            if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_3_DATA, line) != null) {
                                swipeCard.setTrack3Data(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTrackData(line));
                            }
                            break;

                        case PIN_ENTERED:
                            if (transaction != null) {
                                transaction.setLineEnd(index);

                                /*
                                allInformationEntered.setLength(0);
                                allAmountEntered.setLength(0);
                                amountEntered = null;
                                */
                            }
                            break;

                        case INFORMATION_ENTERED:
                            allInformationEntered.append("/").append(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getInformationEntered(line));
                            break;

                        case AMOUNT_ENTERED:
                            allAmountEntered.append("/").append(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getAmountEntered(line));
                            if (!StringUtils.isEmpty(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getAmountEntered(line)))
                                amountEntered = Convert.parseDecimal(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getAmountEntered(line));
                            break;

                        case TRANSACTION_REQUEST:
                            if (transaction != null) {
                                // Transaction end line could be set on pin entered action
                                if (transaction.getLineEnd() == null)
                                    transaction.setLineEnd(index);
                                swipeCard.getTerminalTransactions().add(transaction);
                            }

                            transaction = new TerminalTransaction();
                            if (allInformationEntered.length() > 0)
                                transaction.setAllInformationEntered(allInformationEntered.toString());

                            if (allAmountEntered.length() > 0)
                                transaction.setAllAmountEntered(allAmountEntered.toString());

                            if (amountEntered != null)
                                transaction.setAmountEntered(amountEntered);

                            transaction.setSwipeCard(swipeCard);
                            transaction.setLuno(journalFile.getLuno());
                            transaction.setFileName(journalFile.getFileName());
                            transaction.setLineStart(index + 1);
                            transaction.setOperationState(OperationState.START);
                            transaction.setTransactionDate(journalFile.getJournalDate());
                            DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                            Date requestTime = df.parse(journalFile.getName() + " " + com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTime(line));
                            transaction.setTransactionTime(requestTime);
                            transaction.setTerminalRequestTime(requestTime);
                            transaction.setTransactionRequest(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTransactionRequest(line));
                            OperationCode operationCode = operationCodeMap.get(transaction.getTransactionRequest());
                            if (operationCode == null)
                                throw new OperationCodeNotFoundException(MessageFormat.format(
                                        "ATM operation code not found for [{0}]", transaction.getTransactionRequest()));
                            transaction.setProcessingCode(operationCode.getProcessingCode());

                            /*
                            if (operationCode != null)
                                transaction.setProcessingCode(operationCode.getProcessingCode());
                            else
                                transaction.setProcessingCode(ProcessingCode.NOT_DEFINED);
                            */
                            if (operationCode.getAmount() != null)
                                transaction.setFastCashAmount(operationCode.getAmount());

                            /*
                            put(getTransactionTypeProp(), getTransactionType(transactionRequest));
                            put(getTransactionAmountProp(), String.valueOf(getTransactionAmount(transactionRequest)));
                            put(getTerminalTransactionTimeProp(), getTerminalTransactionTime(line));
                            put(getLineIndexProp(), String.valueOf(iterator.previousIndex()));
                            line = readAtmStatusMessage();
                            */

                            allInformationEntered.setLength(0);
                            allAmountEntered.setLength(0);
                            amountEntered = null;

                            break;

                        // case COMMUNICATION_ERROR:
                        // case COMMUNICATION_OFFLINE:
                        case TERMINAL_MESSAGE:
                            switch (TerminalMessageType.match(line)) {
                                case COMMUNICATION_ERROR:
                                    switch (state) {
                                        case TRANSACTION_REQUEST:
                                            transaction.setCommunicationError(Boolean.TRUE);
                                            break;
                                        default:
                                            throw new UnexpectedLineException(MessageFormat.format("See journal {0}/{1} state:{2} line: {3}", terminal.getLuno(), journalFile.getFileName(), state, index + 1));
                                    }
                                    break;
                                case COMMUNICATION_OFFLINE:
                                    switch (state) {
                                        case TRANSACTION_REQUEST:
                                            transaction.setCommunicationOffline(Boolean.TRUE);
                                            break;
                                    }
                                    break;
                                case CARD_JAMMED:
                                    switch (state) {
                                        case TRANSACTION_START:
                                        case PIN_ENTERED:
                                            swipeCard.setCardJammed(YesNoType.Yes);
                                            break;
                                        case TRANSACTION_REQUEST:
                                            transaction.setCardJammed(YesNoType.Yes);
                                            break;
                                        default:
                                            throw new UnexpectedLineException(MessageFormat.format("See journal {0}/{1} state:{2} line: {3}", terminal.getLuno(), journalFile.getFileName(), state, index + 1));
                                    }
                                    break;
                                case CARD_RETAINED:
                                    switch (state) {
                                        case TRANSACTION_START:
                                        case PIN_ENTERED:
                                            swipeCard.setCardRetained(YesNoType.Yes);
                                            break;
                                        case TRANSACTION_REQUEST:
                                        case RECEIPT:
                                            transaction.setCardRetained(YesNoType.Yes);
                                            break;
                                        default:
                                            throw new UnexpectedLineException(MessageFormat.format("See journal {0}/{1} state:{2} line: {3}", terminal.getLuno(), journalFile.getFileName(), state, index + 1));
                                    }
                                    break;
                                case CASH_RETRACTED:
                                    switch (state) {
                                        case TRANSACTION_REQUEST:
                                        case RECEIPT:
                                            transaction.setCashRetracted(YesNoType.Yes);
                                            break;
                                        default:
                                            throw new UnexpectedLineException(MessageFormat.format("See journal {0}/{1} state:{2} line: {3}", terminal.getLuno(), journalFile.getFileName(), state, index + 1));
                                    }

                                default:
                                    switch (state) {
                                        case TRANSACTION_START:
                                        case PIN_ENTERED:
                                            swipeCard.setTerminalMessage(line);
                                            break;
                                        case TRANSACTION_REQUEST:
                                            transaction.setTerminalMessage(line);
                                            break;
                                    }
                            }
                            break;

                        case TRANSACTION_REPLY:
                            transaction.setTransactionReply(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTransactionReply(line));
                            DateFormat df1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                            Date replyTime = df1.parse(journalFile.getName() + " " + com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTime(line));
                            transaction.setTerminalReplyTime(replyTime);
                            break;

                        case RECEIPT:
                            // Receipt
                            if (transaction.getProcessingCode() == ProcessingCode.CASH_WITHDRAWAL) {
                                transaction.setReceiptTitle(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashWithdrawal(line));

                                MatchResult match;

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_WITHDRAWAL, line);
                                if (match != null)
                                    transaction.setCashWithdrawal(YesNoType.Yes);

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SWITCH_DATE_TIME_LUNO, line);
                                if (match != null) {
                                    transaction.setSwitchDateTime(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getSwitchDateTime(line));
                                    transaction.setReceiptTreminalId(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getTreminalId(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_NO, line);
                                if (match != null) {
                                    transaction.setReceiptCardNumber(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCardNo(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RRN, line);
                                if (match != null) {
                                    transaction.setRrn(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getRrn(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_STAN, line);
                                if (match != null) {
                                    transaction.setStan(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getStan(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT, line);
                                if (match != null) {
                                    transaction.setAmount(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashAmount(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RESPONSE_CODE, line);
                                if (match != null) {
                                    transaction.setResponseCode(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getResponseCode(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RESPONSE, line);
                                if (match != null) {
                                    transaction.setResponse(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getResponse(line));
                                }

                                line = br.readLine();
                                index++;
                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SUCCESSFUL, line);
                                if (match != null) {
                                    transaction.setSuccessful(Boolean.TRUE);
                                    transaction.setSuccess(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getSuccessMessage(line));
                                }

                                match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_UNSUCCESSFUL, line);
                                if (match != null) {
                                    transaction.setSuccessful(Boolean.FALSE);
                                    transaction.setSuccess(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getSuccessMessage(line));
                                }
                            }

                            break;

                        case CASH_REQUEST:
                            transaction.setCashRequest(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashRequest(line));
                            break;

                        case CASH:
                            transaction.setCash(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCash(line));
                            transaction.setCashNoteFromCassetteOne(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashNoteNoFromCassetteOne(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCash(line)));
                            transaction.setCashNoteFromCassetteTwo(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashNoteNoFromCassetteTwo(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCash(line)));
                            transaction.setCashNoteFromCassetteThree(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashNoteNoFromCassetteThree(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCash(line)));
                            transaction.setCashNoteFromCassetteFour(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCashNoteNoFromCassetteFour(com.dpi.financial.ftcom.utility.atm.journal.JournalContent.getCash(line)));
                            break;

                        case CARD_TAKEN:
                            switch (state) {
                                case TRANSACTION_START:
                                case PIN_ENTERED:
                                    swipeCard.setCardTaken(YesNoType.Yes);
                                    break;
                                case TRANSACTION_REQUEST:
                                    swipeCard.setCardTaken(YesNoType.Yes);
                                    transaction.setCardTaken(YesNoType.Yes);
                                    break;
                                default:
                                    throw new UnexpectedLineException(MessageFormat.format("See journal {0}/{1} state:{2} line: {3}", terminal.getLuno(), journalFile.getFileName(), state, index + 1));
                            }
                            break;

                        case CASH_PRESENTED:
                            transaction.setCashPresented(YesNoType.Yes);
                            break;

                        case CASH_TAKEN:
                            transaction.setCashTaken(YesNoType.Yes);
                            break;

                        case CASH_RETRACTED:
                            transaction.setCashRetracted(YesNoType.Yes);

                        case TRANSACTION_END:
                            if (transaction != null)
                                transaction.setLineEnd(index);
                            swipeCard.setLineEnd(index + 1);
                            swipeCard.setOperationState(OperationState.FINISHED);
                            break;


                    }

                    index++;
                }
            } catch (ParseException | MultipleMatchException | IOException e) {
                logger.error("Error", e);
                throw new OperationTerminatedException(MessageFormat.format("Operation failed while preparing swipe card for terminal {0}", terminal.getLuno()));
            } finally {
                if (br != null)
                    br.close();
                br = null;
            }

            if (journalFile.getJournalDate().before(DateUtil.removeTime(journalDateTo))) {
                // journalFile.setState(JournalFileState.PREPARED);
                journalFileService.update(journalFile);
            }
        }

    }

    @Deprecated
    private TerminalTransaction saveTransaction(TerminalTransaction transaction, Long index) {
        transaction.setLineEnd(index);

        if (!transaction.getOperationState().equals(OperationState.ERROR))
            transaction.setOperationState(OperationState.FINISHED);

        System.out.println(
                MessageFormat.format("Terminal transaction: {0}/{1}/{2}/{3}",
                        transaction.getLuno(), transaction.getSwipeCard().getJournalFile().getName(),
                        transaction.getLineStart(), transaction.getLineEnd())
        );

        return terminalTransactionService.create(transaction);
    }

    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param date
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
     * <li>Prepare ATM transactions based on journal content</li>
     */
    public void prepareAtmTransactions(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        logger.info(sdf.format(date));

        String luno = terminal.getLuno();
        try {
            Terminal terminal = terminalService.findByLuno(luno);
            String journalPath = configuration.getJournalPath();
            prepareAtmTransactions(terminal);
            getJournalFileList().clear();
            setJournalFileList(getJournalFileList(journalPath, terminal));
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }

    }

    public Date getJournalDateFrom() {
        return journalDateFrom;
    }

    public void setJournalDateFrom(Date journalDateFrom) {
        this.journalDateFrom = journalDateFrom;
    }

    public Date getJournalDateTo() {
        return journalDateTo;
    }

    public void setJournalDateTo(Date journalDateTo) {
        this.journalDateTo = journalDateTo;
    }

    public List<JournalFile> getJournalFileList() {
        return entityList;
    }

    public void setJournalFileList(List<JournalFile> journalFileList) {
        this.entityList = journalFileList;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }


    ////////////////////////////////
    // CHECK METHOT BELOW
    ////////////////////////////////

    /**
     * This method sync files on disk to database JOURNAL_FILE and
     * return list of journal file info in journal date order.
     *
     * @param baseFolder
     * @param terminal
     * @return
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Monday, December 05, 2016
     * <li>Prepare ATM transactions based on journal content</li>
     */
    public List<JournalFile> getJournalFileList(String baseFolder, Terminal terminal) {
        logger.info(MessageFormat.format("Find all physical journal file for {0} in path {1}",
                terminal.getLuno(), baseFolder));
        List<JournalFile> journalFiles = service.findAll(terminal);

        JournalPhysicalFile journalPhysicalFile = new JournalPhysicalFile(baseFolder);
        try {
            List<File> journalPhysicalFiles = journalPhysicalFile.getOrderedJournalFiles(terminal.getLuno());
            for (File file : journalPhysicalFiles) {
                JournalFile journal = new JournalFile();

                journal.setLuno(terminal.getLuno());
                journal.setName(FilenameUtils.getBaseName(file.getName()));
                journal.setFileName(file.getName());
                journal.setTerminal(terminal);
                updateFileInfo(journal, file);

                // journal.setState(JournalFileState.ENTRY);

                // http://stackoverflow.com/questions/13138990/how-to-search-in-a-list-of-java-object
                /*
                List<JournalFile> result = journalFiles.stream()
                        .filter(item -> item.getFileName().equals(journal.getFileName()))
                        .collect(Collectors.toList());
                */
                Optional<JournalFile> any = journalFiles.stream()
                        .filter(item -> item.getFileName().equals(journal.getFileName()))
                        .findAny();

                if (any.isPresent()) {
                    JournalFile journalFile = any.get();
                    System.out.println(
                            MessageFormat.format("Journal {0}/{1} found.", journalFile.getLuno(), journalFile.getFileName())
                    );
                    System.out.println(
                            MessageFormat.format("{0}/{1}", journalFile.getMd5(), journal.getMd5())
                    );
                    if (!journalFile.getMd5().equals(journal.getMd5())) {
                        System.out.println(
                                MessageFormat.format("Journal {0}/{1} content is different.", journalFile.getLuno(), journalFile.getFileName())
                        );
                        updateFileInfo(journalFile, file);
                        // journalFile.setState(JournalFileState.ENTRY);
                        service.update(journalFile);
                    }
                } else {
                    JournalFile journalFile = service.create(journal);
                    System.out.println(
                            MessageFormat.format("Journal {0}/{1} added.", journalFile.getLuno(), journalFile.getFileName())
                    );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        journalFiles = service.findAll(terminal);
        return journalFiles;
    }

    public void listFilesForFolder(final File folder, final boolean recurse) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() && recurse) {
                listFilesForFolder(fileEntry, recurse);
            } else {
                System.out.println(fileEntry.getAbsolutePath());
                System.out.println(fileEntry.getParent() + "\\" + fileEntry.getName());
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // MOVE TO ANOTHER CLASS
    ////////////////////////////////////////////////////////////////////////////

    int getLineNumber(File file) throws IOException {
        BufferedReader reader = null;
        int lines = 0;
        try {
            reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            while (reader.readLine() != null)
                lines++;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return lines;
    }

    int getLineNumber2(File file) throws IOException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(file.getAbsolutePath()));
            while ((reader.readLine()) != null)
                ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return reader.getLineNumber();
    }

    public int countLineNumber(File file) throws IOException {
        // http://www.technicalkeeda.com/java/how-to-count-total-number-of-lines-of-file-using-java
        LineNumberReader lineNumberReader = null;
        int lines = 0;
        try {
            lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Long.MAX_VALUE);
            lines = lineNumberReader.getLineNumber();

        } catch (IOException e) {
            System.out.println("IOException Occured" + e.getMessage());
        } finally {
            if (lineNumberReader != null)
                lineNumberReader.close();
        }

        return lines;
    }

    private int getMaxLineNumber(String terminal, String fileNameWithOutExt) {
        int result = 0;

        /**
         PreparedStatement ps = null;
         try {
         String sqlQry = "SELECT MAX(LINENUMBER) FROM ATM_OPERATION_MESSAGE O WHERE O.TERMINALID = ? AND O.FILENAME = ?";
         ps = DataMgr.conn.prepareStatement(sqlQry);
         ps.setString(1, terminal);
         ps.setString(2, fileNameWithOutExt);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
         result = rs.getInt(1);
         }
         } catch (SQLException e) {
         e.printStackTrace();
         } finally {
         try {
         if (ps != null)
         ps.close();
         } catch (SQLException e) {
         e.printStackTrace();
         }
         }
         */

        return result;
    }

    public String calcMD5() throws Exception {
        byte[] buffer = new byte[8192];
        MessageDigest md = MessageDigest.getInstance("MD5");

        DigestInputStream dis = new DigestInputStream(new FileInputStream(new File("Path to file")), md);
        try {
            while (dis.read(buffer) != -1) ;
        } finally {
            dis.close();
        }

        byte[] bytes = md.digest();

        // bytesToHex-method
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            /* comment by Hossein - uncomment it
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            */
        }

        return new String(hexChars);
    }

    private TerminalOperationState getNextOperationState(TerminalTransactionState state, TerminalOperationType operationType) {
        TerminalTransactionState followingState = TerminalTransactionState.NOT_DEFINED;

        if (state == null || state == TerminalTransactionState.NOT_DEFINED)
            return null;

        TerminalOperationState terminalOperationState;

        try {
            terminalOperationState = terminalOperationStateService.findByStateAndOperation(state, operationType);

            followingState = terminalOperationState.getFollowingState();
            terminalOperationState.setCount(terminalOperationState.getCount() + 1);
            terminalOperationStateService.update(terminalOperationState);
        } catch (Exception e) {
            logger.error("No entity found for query", e);

            terminalOperationState = new TerminalOperationState();
            terminalOperationState.setCurrentState(state);
            terminalOperationState.setOperationType(operationType);
            terminalOperationState.setFollowingState(TerminalTransactionState.NOT_DEFINED);
            terminalOperationState.setUserAction(UserAction.NOT_DEFINED);
            terminalOperationState.setCount(1L);
            terminalOperationState = terminalOperationStateService.create(terminalOperationState);
        }

        return terminalOperationState;

        /*
        if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_IGNORE, line) != null)
            return currentState;

        if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TERMINAL_MESSAGE, line) != null)
            return currentState;
*/
        /*
        if (db.getNextState(cur, pattern) != null)
            nextState = db.getNextState(cur, pattern);
        */

        /*
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
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_APPLICATION_STARTED, line) != null)
                    nextState = TerminalTransactionState.IDLE;
                else
                    nextState = currentState;
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
                else if (RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_START, line) != null)
                    nextState = TerminalTransactionState.TRANSACTION_START;
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
*/
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

    }
}
