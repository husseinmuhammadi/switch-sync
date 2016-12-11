package com.dpi.financial.ftcom.model.to.atm.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.OperationStateConverter;
import com.dpi.financial.ftcom.model.type.OperationState;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "TERMINAL_TRANSACTION_SEQ")
@Table(name = "TERMINAL_TRANSACTION", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "FILE_NAME", "LINE_START"}),
        @UniqueConstraint(columnNames = {"SWIPE_CARD_ID", "LINE_START"})
})
@NamedQueries({
        @NamedQuery(name = TerminalTransaction.FIND_ALL, query = "select t from TerminalTransaction t where t.deleted = false")
})
public class TerminalTransaction extends EntityBase {
    public static final String FIND_ALL = "JournalTransaction.findAll";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SWIPE_CARD_ID", nullable = false)
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

    @Column(name = "LINE_START", nullable = false)
    private Long lineStart;

    @Column(name = "LINE_END", nullable = false)
    private Long lineEnd;

    @Column(name = "OPERATION_STATE", nullable = false, length = 1)
    @Convert(converter = OperationStateConverter.class)
    private OperationState operationState;

    @Column(name = "TRANSACTION_REQUEST", length = 8)
    private String transactionRequest;

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

}