package com.dpi.financial.ftcom.model.to.atm.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.OperationStateConverter;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.converter.YesNoTypeConverter;
import com.dpi.financial.ftcom.model.type.OperationState;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.YesNoType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "TERMINAL_TRANSACTION_SEQ")
@Table(name = "TERMINAL_TRANSACTION", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "FILE_NAME", "LINE_START"}, name = "UK_TERMINAL_TRANSACTION_LFLS"),
        @UniqueConstraint(columnNames = {"SWIPE_CARD_ID", "LINE_START"}, name = "UK_TERMINAL_TRANSACTION_SLS")
})
@NamedQueries({
        @NamedQuery(name = TerminalTransaction.FIND_ALL, query = "select t from TerminalTransaction t where t.deleted = false")
})
public class TerminalTransaction extends EntityBase {
    public static final String FIND_ALL = "JournalTransaction.findAll";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SWIPE_CARD_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_SC_TT"))
    private SwipeCard swipeCard;

    /*
    @OneToOne
    @JoinColumn(name = "TERMINAL_ID")
    private Terminal terminal;
    */

    // Logical Unit Number
    @Column(name = "LUNO", nullable = false, length = 10)
    private String luno;

    /*
    @Column(name = "NAME", nullable = false, length = 8)
    private String name;
    */

    @Column(name = "FILE_NAME", nullable = false, length = 12)
    private String fileName;

    @Temporal(TemporalType.DATE)
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private Date transactionDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_TIME", nullable = false)
    private Date transactionTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TERMINAL_REQUEST_TIME")
    private Date terminalRequestTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TERMINAL_REPLY_TIME")
    private Date terminalReplyTime;

    @Column(name = "LINE_START", nullable = false)
    private Long lineStart;

    @Column(name = "LINE_END", nullable = false)
    private Long lineEnd;

    @Column(name = "OPERATION_STATE", nullable = false, length = 1)
    @Convert(converter = OperationStateConverter.class)
    private OperationState operationState;

    @Column(name = "TRANSACTION_REQUEST", length = 8)
    private String transactionRequest;

    @Column(name = "TRANSACTION_REPLY", length = 100)
    private String transactionReply;

    @Column(name = "PROCESSING_CODE", nullable = false, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @Column(name = "FAST_CASH_AMOUNT")
    private BigDecimal fastCashAmount;

    @Column(name = "CASH_REQUEST", length = 8)
    private String cashRequest;

    @Column(name = "CASH", length = 24)
    private String cash;

    @Column(name = "CASH_NOTE_FROM_CASSETTE_ONE")
    private Long cashNoteFromCassetteOne;

    @Column(name = "CASH_NOTE_FROM_CASSETTE_TWO")
    private Long cashNoteFromCassetteTwo;

    @Column(name = "CASH_NOTE_FROM_CASSETTE_THREE")
    private Long cashNoteFromCassetteThree;

    @Column(name = "CASH_NOTE_FROM_CASSETTE_FOUR")
    private Long cashNoteFromCassetteFour;

    @Column(name = "CARD_TAKEN", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cardTaken;

    @Column(name = "CASH_PRESENTED", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cashPresented;

    @Column(name = "CASH_TAKEN", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cashTaken;

    @Column(name = "CASH_RETRACTED", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cashRetracted;

    @Column(name = "CARD_RETAINED", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cardRetained;

    @Column(name = "CARD_JAMMED", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cardJammed;

    @Column(name = "CASH_WITHDRAWAL", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType cashWithdrawal;

    @Column(name = "RECEIPT_TITLE", length = 100)
    private String receiptTitle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SWITCH_DATE_TIME")
    private Date switchDateTime;

    @Column(name = "RECEIPT_TREMINAL_ID", length = 5)
    private String receiptTreminalId;

    @Column(name = "RECEIPT_CARD_NUMBER", length = 19)
    private String receiptCardNumber;

    // @Column(name = "RETRIEVAL_REFERENCE_NUMBER", length = 12)
    @Column(name = "RRN", length = 12)
    private String rrn;

    // @Column(name = "SYSTEM_TRACE_AUDIT_NUMBER")
    @Column(name = "STAN")
    private Integer stan;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "RESPONSE_CODE", length = 2)
    private String responseCode;

    @Column(name = "RESPONSE", length = 100)
    private String response;

    @Column(name = "SUCCESS", length = 20)
    private String success;

    @Column(name = "IS_SUCCESSFUL") // , columnDefinition = "NUMBER(1,0) default 0")
    private Boolean successful;

    @Column(name = "COMMUNICATION_ERROR", columnDefinition = "NUMBER(1,0) default 0")
    private Boolean communicationError = Boolean.FALSE;

    @Column(name = "COMMUNICATION_OFFLINE", columnDefinition = "NUMBER(1,0) default 0")
    private Boolean communicationOffline = Boolean.FALSE;

    @Column(name = "ALL_INFORMATION_ENTERED", length = 200)
    private String allInformationEntered;

    @Column(name = "ALL_AMOUNT_ENTERED", length = 200)
    private String allAmountEntered;

    @Column(name = "AMOUNT_ENTERED")
    private BigDecimal amountEntered;

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public SwipeCard getSwipeCard() {
        return swipeCard;
    }

    public void setSwipeCard(SwipeCard swipeCard) {
        this.swipeCard = swipeCard;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Long getLineStart() {
        return lineStart;
    }

    public void setLineStart(Long lineStart) {
        this.lineStart = lineStart;
    }

    public Long getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(Long lineEnd) {
        this.lineEnd = lineEnd;
    }

    public OperationState getOperationState() {
        return operationState;
    }

    public void setOperationState(OperationState operationState) {
        this.operationState = operationState;
    }

    public String getTransactionRequest() {
        return transactionRequest;
    }

    public void setTransactionRequest(String transactionRequest) {
        this.transactionRequest = transactionRequest;
    }

    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public BigDecimal getFastCashAmount() {
        return fastCashAmount;
    }

    public void setFastCashAmount(BigDecimal fastCashAmount) {
        this.fastCashAmount = fastCashAmount;
    }

    public String getTransactionReply() {
        return transactionReply;
    }

    public void setTransactionReply(String transactionReply) {
        this.transactionReply = transactionReply;
    }

    public String getCashRequest() {
        return cashRequest;
    }

    public void setCashRequest(String cashRequest) {
        this.cashRequest = cashRequest;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public YesNoType getCardTaken() {
        return cardTaken;
    }

    public void setCardTaken(YesNoType cardTaken) {
        this.cardTaken = cardTaken;
    }

    public YesNoType getCashPresented() {
        return cashPresented;
    }

    public void setCashPresented(YesNoType cashPresented) {
        this.cashPresented = cashPresented;
    }

    public Long getCashNoteFromCassetteOne() {
        return cashNoteFromCassetteOne;
    }

    public void setCashNoteFromCassetteOne(Long cashNoteFromCassetteOne) {
        this.cashNoteFromCassetteOne = cashNoteFromCassetteOne;
    }

    public Long getCashNoteFromCassetteTwo() {
        return cashNoteFromCassetteTwo;
    }

    public void setCashNoteFromCassetteTwo(Long cashNoteFromCassetteTwo) {
        this.cashNoteFromCassetteTwo = cashNoteFromCassetteTwo;
    }

    public Long getCashNoteFromCassetteThree() {
        return cashNoteFromCassetteThree;
    }

    public void setCashNoteFromCassetteThree(Long cashNoteFromCassetteThree) {
        this.cashNoteFromCassetteThree = cashNoteFromCassetteThree;
    }

    public Long getCashNoteFromCassetteFour() {
        return cashNoteFromCassetteFour;
    }

    public void setCashNoteFromCassetteFour(Long cashNoteFromCassetteFour) {
        this.cashNoteFromCassetteFour = cashNoteFromCassetteFour;
    }

    public YesNoType getCashWithdrawal() {
        return cashWithdrawal;
    }

    public void setCashWithdrawal(YesNoType cashWithdrawal) {
        this.cashWithdrawal = cashWithdrawal;
    }

    public String getReceiptTitle() {
        return receiptTitle;
    }

    public void setReceiptTitle(String receiptTitle) {
        this.receiptTitle = receiptTitle;
    }

    public String getReceiptTreminalId() {
        return receiptTreminalId;
    }

    public void setReceiptTreminalId(String receiptTreminalId) {
        this.receiptTreminalId = receiptTreminalId;
    }

    public String getReceiptCardNumber() {
        return receiptCardNumber;
    }

    public void setReceiptCardNumber(String receiptCardNumber) {
        this.receiptCardNumber = receiptCardNumber;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public Integer getStan() {
        return stan;
    }

    public void setStan(Integer stan) {
        this.stan = stan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Date getSwitchDateTime() {
        return switchDateTime;
    }

    public void setSwitchDateTime(Date switchDateTime) {
        this.switchDateTime = switchDateTime;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public YesNoType getCashTaken() {
        return cashTaken;
    }

    public void setCashTaken(YesNoType cashTaken) {
        this.cashTaken = cashTaken;
    }

    public Boolean getCommunicationError() {
        return communicationError;
    }

    public void setCommunicationError(Boolean communicationError) {
        this.communicationError = communicationError;
    }

    public Boolean getCommunicationOffline() {
        return communicationOffline;
    }

    public void setCommunicationOffline(Boolean communicationOffline) {
        this.communicationOffline = communicationOffline;
    }

    public Date getTerminalRequestTime() {
        return terminalRequestTime;
    }

    public void setTerminalRequestTime(Date terminalRequestTime) {
        this.terminalRequestTime = terminalRequestTime;
    }

    public Date getTerminalReplyTime() {
        return terminalReplyTime;
    }

    public void setTerminalReplyTime(Date terminalReplyTime) {
        this.terminalReplyTime = terminalReplyTime;
    }

    public String getAllInformationEntered() {
        return allInformationEntered;
    }

    public void setAllInformationEntered(String allInformationEntered) {
        this.allInformationEntered = allInformationEntered;
    }

    public String getAllAmountEntered() {
        return allAmountEntered;
    }

    public void setAllAmountEntered(String allAmountEntered) {
        this.allAmountEntered = allAmountEntered;
    }

    public BigDecimal getAmountEntered() {
        return amountEntered;
    }

    public void setAmountEntered(BigDecimal amountEntered) {
        this.amountEntered = amountEntered;
    }

    public YesNoType getCashRetracted() {
        return cashRetracted;
    }

    public void setCashRetracted(YesNoType cashRetracted) {
        this.cashRetracted = cashRetracted;
    }

    public YesNoType getCardRetained() {
        return cardRetained;
    }

    public void setCardRetained(YesNoType cardRetained) {
        this.cardRetained = cardRetained;
    }

    public YesNoType getCardJammed() {
        return cardJammed;
    }

    public void setCardJammed(YesNoType cardJammed) {
        this.cardJammed = cardJammed;
    }
}