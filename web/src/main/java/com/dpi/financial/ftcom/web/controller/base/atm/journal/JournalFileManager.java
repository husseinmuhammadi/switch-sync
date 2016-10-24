package com.dpi.financial.ftcom.web.controller.base.atm.journal;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.JournalFileService;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.model.to.atm.JournalFile;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import javafx.scene.layout.BorderImage;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

@Named
@ViewScoped
public class JournalFileManager extends ControllerManagerBase<JournalFile> implements Serializable {

    @EJB
    private JournalFileService service;

    @EJB
    private TerminalService terminalService;

    private Terminal terminal;
    private List<JournalFile> journalFileList;

    public JournalFileManager() {
        super(JournalFile.class);

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
            terminal = terminalService.findByLuno(terminal.getLuno());
            journalFileList = service.findAll(terminal);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("atm.properties");
            Properties properties = new Properties();
            properties.load(input);
            String path = properties.getProperty("JOURNAL_PATH");

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
}
