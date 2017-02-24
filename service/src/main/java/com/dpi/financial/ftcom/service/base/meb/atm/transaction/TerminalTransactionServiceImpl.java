package com.dpi.financial.ftcom.service.base.meb.atm.transaction;

import com.dpi.financial.ftcom.api.base.atm.ndc.OperationCodeService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.atm.TerminalDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.SwipeCardDao;
import com.dpi.financial.ftcom.model.dao.meb.atm.transaction.TerminalTransactionDao;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.SwipeCard;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Local(TerminalTransactionService.class)
public class TerminalTransactionServiceImpl extends GeneralServiceImpl<TerminalTransaction>
        implements TerminalTransactionService {

    Logger logger = LoggerFactory.getLogger(TerminalTransactionServiceImpl.class);

    @EJB
    private JournalFileService journalFileService;

    @EJB
    private OperationCodeService operationCodeService;

    @EJB
    private TerminalTransactionDao dao;

    @EJB
    SwipeCardDao swipeCardDao;

    @EJB
    TerminalDao terminalDao;

    @Override
    public GenericDao<TerminalTransaction> getGenericDao() {
        return dao;
    }


    @Override
    public List<TerminalTransaction> findAllByLunoCardNumber(String luno, String pan) {
        return dao.findAllByLunoCardNumber(luno, pan);
    }

    @Override
    public void saveSwipeCardAndTransactions(SwipeCard swipeCard) {

        SwipeCard swipeCard1 = swipeCardDao.create(swipeCard);

        // Update terminal last transaction
        Terminal terminal = terminalDao.findById(swipeCard1.getJournalFile().getTerminal().getId());
        terminal.setLastJournalDate(swipeCard1.getJournalFile().getJournalDate());
        terminal.setLastJournalLineNumber(swipeCard1.getLineEnd());
        terminalDao.update(terminal);
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
