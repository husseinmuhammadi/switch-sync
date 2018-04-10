package com.dpi.financial.ftcom.model.to.meb.isc.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.type.ProcessingCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "MEB_SWITCH_TRANSACTION_SEQ")
@Table(name = "MEB_SWITCH_TRANSACTION", uniqueConstraints = {
        // @UniqueConstraint(columnNames = {"LUNO", "FILE_NAME", "LINE_START"}, name = "UK_TERMINAL_TRANSACTION_LFLS"),
        // @UniqueConstraint(columnNames = {"SWIPE_CARD_ID", "LINE_START"}, name = "UK_TERMINAL_TRANSACTION_SLS")
})
@NamedQueries({
        @NamedQuery(name = MiddleEastBankSwitchTransaction.FIND_ALL,
                query = "select t from MiddleEastBankSwitchTransaction t where t.deleted = false"),
        @NamedQuery(name = MiddleEastBankSwitchTransaction.FIND_ALL_UNBOUND,
                query = "select t.cardNumber from MiddleEastBankSwitchTransaction t where t.deleted = false and t.terminalTransaction is null and t.luno = :luno and t.transactionDate between :transactionDateFrom and :transactionDateTo group by t.luno, t.cardNumber order by count(*) desc"),
        @NamedQuery(name = MiddleEastBankSwitchTransaction.FIND_ALL_BY_LUNO_CARD_NUMBER,
                query = "select t from MiddleEastBankSwitchTransaction t where t.deleted = false and t.luno = :luno and t.cardNumber = :cardNumber order by t.transactionDate asc"),
        @NamedQuery(name = MiddleEastBankSwitchTransaction.FIND_INCONSISTENT_TRANSACTIONS,
                query = "select t from MiddleEastBankSwitchTransaction t where t.deleted = false " +
                        "and t.processingCode = com.dpi.financial.ftcom.model.type.ProcessingCode.CASH_WITHDRAWAL " +
                        "and coalesce(t.responseCode, '91') = '00' and t.reveresed = false " +
                        "and coalesce(t.terminalTransaction.cashTaken, 'N') = com.dpi.financial.ftcom.model.type.YesNoType.No " +
                        "and t.luno = :luno and t.transactionDate between :transactionDateFrom and :transactionDateTo"),
})
public class MiddleEastBankSwitchTransaction extends EntityBase {
    public static final String FIND_ALL = "MiddleEastBankSwitchTransaction.findAll";
    public static final String FIND_ALL_UNBOUND = "MiddleEastBankSwitchTransaction.findAllUnbound";
    public static final String FIND_ALL_BY_LUNO_CARD_NUMBER = "MiddleEastBankSwitchTransaction.findAllByLunoCardNumber";
    public static final java.lang.String FIND_INCONSISTENT_TRANSACTIONS = "MiddleEastBankSwitchTransaction.findInconsistentTransactions";

    // https://en.wikibooks.org/wiki/Java_Persistence/OneToOne
    // Each switch transaction has a terminal transaction
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERMINAL_TRANSACTION_ID")
    private TerminalTransaction terminalTransaction;

    // Logical Unit Number
    @Column(name = "LUNO", nullable = false, length = 10)
    private String luno;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private Date transactionDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTERED_DATE_TIME", nullable = false)
    private Date enteredDateTime;

    @Column(name = "PROCESSING_CODE", nullable = false, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARD_MASTER_ID")
    private CardMaster cardMaster;

    @Column(name = "CARD_NUMBER", length = 19)
    private String cardNumber;

    @Column(name = "SOURCE_CARD", length = 19)
    private String sourceCard;

    @Column(name = "DESTINATION_CARD", length = 19)
    private String destinationCard;

    // @Column(name = "RETRIEVAL_REFERENCE_NUMBER", length = 12)
    @Column(name = "RRN", length = 12)
    private String rrn;

    // @Column(name = "SYSTEM_TRACE_AUDIT_NUMBER")
    @Column(name = "STAN")
    private Integer stan;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "RESPONSE_CODE", length = 2)
    private String responseCode;

    @Column(name = "REVERESED")
    private Boolean reveresed;

    @Column(name = "TYPE_OF_TRANSACTION", length = 2)
    private String typeOfTransaction;

    @Column(name = "ACQUIRING_INSTITUTION_CODE", length = 11)
    private String acquiringInstitutionCode;

    public TerminalTransaction getTerminalTransaction() {
        return terminalTransaction;
    }

    public void setTerminalTransaction(TerminalTransaction terminalTransaction) {
        this.terminalTransaction = terminalTransaction;
    }

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getEnteredDateTime() {
        return enteredDateTime;
    }

    public void setEnteredDateTime(Date enteredDateTime) {
        this.enteredDateTime = enteredDateTime;
    }

    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSourceCard() {
        return sourceCard;
    }

    public void setSourceCard(String sourceCard) {
        this.sourceCard = sourceCard;
    }

    public String getDestinationCard() {
        return destinationCard;
    }

    public void setDestinationCard(String destinationCard) {
        this.destinationCard = destinationCard;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Boolean getReveresed() {
        return reveresed;
    }

    public void setReveresed(Boolean reveresed) {
        this.reveresed = reveresed;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getAcquiringInstitutionCode() {
        return acquiringInstitutionCode;
    }

    public void setAcquiringInstitutionCode(String acquiringInstitutionCode) {
        this.acquiringInstitutionCode = acquiringInstitutionCode;
    }

    public CardMaster getCardMaster() {
        return cardMaster;
    }

    public void setCardMaster(CardMaster cardMaster) {
        this.cardMaster = cardMaster;
    }

    @Transient
    private int atmTransactionIndex = -1;

    public int getAtmTransactionIndex() {
        return atmTransactionIndex;
    }

    public void setAtmTransactionIndex(int atmTransactionIndex) {
        this.atmTransactionIndex = atmTransactionIndex;
    }

    @Transient
    public int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}