package com.dpi.financial.ftcom.web.controller.base.swt.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.api.base.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.api.base.swt.reconciliation.SwitchReconciliationService;
import com.dpi.financial.ftcom.api.base.swt.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.swt.transaction.SwitchTransaction;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;

import javax.ejb.EJB;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class SwitchReconciliationManager extends ControllerManagerBase<SwitchTransaction>
        implements Serializable {

    @EJB
    private SwitchReconciliationService service;

    @EJB
    private TerminalService terminalService;

    @EJB
    private SwitchTransactionService switchTransactionService;

    @EJB
    private TerminalTransactionService terminalTransactionService;

    private Terminal terminal;
    private List<String> cardNumbers;
    private String selectedCardNumber;
    Date switchTransactionDateFrom;
    Date switchTransactionDateTo;

    private List<SwitchTransaction> switchTransactionList;
    private List<TerminalTransaction> terminalTransactionList;

    public SwitchReconciliationManager() {
        super(SwitchTransaction.class);

        terminal = new Terminal();

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        switchTransactionDateFrom = cal.getTime();

        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        switchTransactionDateTo = cal.getTime();
    }

    @Override
    public GeneralServiceApi<SwitchTransaction> getGeneralServiceApi() {
        return service;
    }

    /**
     * This method initialize list not depend on the request parameter
     */
    @Override
    public void init() {

    }

    public void onLoad() {
    }

    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param event
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Tuesday, December 13, 2016 3:10:02 PM
     * <li>Synchronize ATM transactions based on switch transactions</li>
     */
    public void lunoValueChange(AjaxBehaviorEvent event) {
        String luno = ((UIInput) event.getComponent()).getValue().toString();
        try {
            // Terminal terminal = terminalService.findByLuno(luno);
            cardNumbers = service.findAllCard(luno, switchTransactionDateFrom, switchTransactionDateTo);
            cardNumbers = new ArrayList<String>(cardNumbers.subList(0, 99));
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
    public void cardValueChange(AjaxBehaviorEvent event) {
        String cardNumber = ((UIInput) event.getComponent()).getValue().toString();
        try {
            switchTransactionList = switchTransactionService.findAllByLunoCardNumber(terminal.getLuno(), cardNumber);
            terminalTransactionList = terminalTransactionService.findAllByLunoCardNumber(terminal.getLuno(), cardNumber);
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
    public void synchronizeSwitchTransactions(AjaxBehaviorEvent event) {
        String luno = terminal.getLuno();
        try {
            service.synchronizeAtmTransactions(luno, selectedCardNumber);
            switchTransactionList = switchTransactionService.findAllByLunoCardNumber(terminal.getLuno(), selectedCardNumber);
            terminalTransactionList = terminalTransactionService.findAllByLunoCardNumber(terminal.getLuno(), selectedCardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public List<SwitchTransaction> getSwitchTransactionList() {
        return switchTransactionList;
    }

    public void setSwitchTransactionList(List<SwitchTransaction> switchTransactionList) {
        this.switchTransactionList = switchTransactionList;
    }

    public List<TerminalTransaction> getTerminalTransactionList() {
        return terminalTransactionList;
    }

    public void setTerminalTransactionList(List<TerminalTransaction> terminalTransactionList) {
        this.terminalTransactionList = terminalTransactionList;
    }

    public List<String> getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(List<String> cardNumbers) {
        this.cardNumbers = cardNumbers;
    }

    public Date getSwitchTransactionDateFrom() {
        return switchTransactionDateFrom;
    }

    public void setSwitchTransactionDateFrom(Date switchTransactionDateFrom) {
        this.switchTransactionDateFrom = switchTransactionDateFrom;
    }

    public Date getSwitchTransactionDateTo() {
        return switchTransactionDateTo;
    }

    public void setSwitchTransactionDateTo(Date switchTransactionDateTo) {
        this.switchTransactionDateTo = switchTransactionDateTo;
    }

    public String getSelectedCardNumber() {
        return selectedCardNumber;
    }

    public void setSelectedCardNumber(String selectedCardNumber) {
        this.selectedCardNumber = selectedCardNumber;
    }
}
