package com.dpi.financial.ftcom.web.controller.base.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.api.base.atm.TerminalTransactionService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.journal.JournalFile;
import com.dpi.financial.ftcom.utility.date.DateUtil;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import com.dpi.financial.ftcom.web.controller.conf.AtmConfiguration;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Named
@ViewScoped
public class JournalFileManager extends ControllerManagerBase<JournalFile> implements Serializable {

    @EJB
    private JournalFileService service;

    @EJB
    private TerminalService terminalService;

    @EJB
    TerminalTransactionService terminalTransactionService;

    @Inject
    private AtmConfiguration configuration;

    private Terminal terminal;

    private List<JournalFile> journalFileList;

    Date journalDateFrom;
    Date journalDateTo;

    public JournalFileManager() {
        super(JournalFile.class);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        journalDateFrom = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 10);
        journalDateTo = cal.getTime();

        terminal = new Terminal();
    }

    @Override
    public GeneralServiceApi<JournalFile> getGeneralServiceApi() {
        return service;
    }

    //TODO: Make this method private
    @Override
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
    }

    public void onLoad() {
        try {
            Terminal terminal = terminalService.findByLuno(this.terminal.getLuno());
            journalFileList = service.findAll(terminal);

            String path = configuration.getJournalPath();
            // journalFileList = service.getJournalFileList(path);
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public List<JournalFile> getJournalFileList() {
        return journalFileList;
    }

    public void setJournalFileList(List<JournalFile> journalFileList) {
        this.journalFileList = journalFileList;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public void lunoValueChange(AjaxBehaviorEvent event) {
        String luno = ((UIInput) event.getComponent()).getValue().toString();
        System.out.println("Selected luno: " + luno);
        System.out.println("Selected luno: " + getTerminal().getLuno());
        try {
            Terminal terminal = terminalService.findByLuno(luno);

            String journalPath = configuration.getJournalPath();
            journalFileList.clear();
            journalFileList = service.getJournalFileList(journalPath, terminal);
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
            terminalTransactionService.prepareAtmTransactions(journalPath, terminal, journalDateFrom, journalDateTo);
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param event
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Tuesday, December 13, 2016 3:10:02 PM
     * <li>Synchronize ATM transactions based on switch transactions</li>
     */
    public void synchronizeAtmTransactions(AjaxBehaviorEvent event) {
        String luno = terminal.getLuno();
        try {
            Terminal terminal = terminalService.findByLuno(luno);
            String journalPath = configuration.getJournalPath();
            terminalTransactionService.synchronizeAtmTransactions(journalDateFrom, journalDateTo);
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
