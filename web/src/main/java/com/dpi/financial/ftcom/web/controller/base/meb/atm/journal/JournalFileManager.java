package com.dpi.financial.ftcom.web.controller.base.meb.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.api.base.meb.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.api.base.meb.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.journal.JournalFile;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import com.dpi.financial.ftcom.web.controller.conf.AtmConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Named("middleEastBankJournalFileManager")
@ViewScoped
public class JournalFileManager extends ControllerManagerBase<JournalFile> implements Serializable {

    private static final long serialVersionUID = -3032343937325405624L;

    Logger logger = LoggerFactory.getLogger(JournalFileManager.class);

    @EJB
    private JournalFileService service;

    @EJB
    private TerminalService terminalService;

    @EJB
    TerminalTransactionService terminalTransactionService;

    @EJB
    SwitchTransactionService switchTransactionService;

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

    public void lunoValueChange(AjaxBehaviorEvent event) {
        List<JournalFile> journalFiles = getJournalFileList();
        if (journalFiles != null && !journalFiles.isEmpty())
            journalFiles.clear();

        String luno = ((UIInput) event.getComponent()).getValue().toString();
        logger.info(MessageFormat.format("Get all journal file for logical unit number: {0}/{1}", luno, getTerminal().getLuno()));
        try {
            Terminal terminal = terminalService.findByLuno(luno);
            setJournalFileList(service.findAll(terminal));
            // } catch (JournalFilesNotExists e) {
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public String getJournalPath() {
        String path = null;

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("atm.properties");
            Properties properties = new Properties();
            properties.load(input);
            path = properties.getProperty("JOURNAL_PATH");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
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
            String journalPath = configuration.getJournalPath();

            Date current = journalDateFrom;

            while (current.before(journalDateTo)) {
                terminalTransactionService.prepareAtmTransactions(journalPath, terminal, current, current);
                current = DateUtil.addDays(current, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
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
        System.out.println(":::::::::" + sdf.format(date));

        String luno = terminal.getLuno();
        try {
            Terminal terminal = terminalService.findByLuno(luno);
            String journalPath = configuration.getJournalPath();
            terminalTransactionService.prepareAtmTransactions(journalPath, terminal, DateUtil.getDate(2014, Month.FEBRUARY, 1), date);
            getJournalFileList().clear();
            setJournalFileList(service.getJournalFileList(journalPath, terminal));
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
}
