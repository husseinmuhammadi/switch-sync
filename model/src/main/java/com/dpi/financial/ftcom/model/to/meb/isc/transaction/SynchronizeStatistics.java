package com.dpi.financial.ftcom.model.to.meb.isc.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.type.ProcessingCode;

import javax.persistence.*;


@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "MEB_SYNCHRONIZE_STATISTICS_SEQ")
@Table(name = "MEB_SYNCHRONIZE_STATISTICS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LUNO", "CARD_NUMBER", "PROCESSING_CODE"})
})
@NamedQueries({
        @NamedQuery(name = SynchronizeStatistics.FIND_ALL,
                query = "from SynchronizeStatistics where deleted = false"
        ),
        @NamedQuery(name = SynchronizeStatistics.FIND_BY_LUNO_PROCESSING_CODE,
                query = "from SynchronizeStatistics where deleted = false and luno = :luno order by retryCount"
        ),
        @NamedQuery(name = SynchronizeStatistics.FIND_BY_LUNO_CARD_NUMBER_PROCESSING_CODE,
                query = "from SynchronizeStatistics where deleted = false and luno = :luno and cardNumber = :cardNumber and processingCode = :processingCode"
        ),
})
public class SynchronizeStatistics extends EntityBase {
    public static final String FIND_ALL = "SynchronizeStatistics.findAll";
    public static final String FIND_BY_LUNO_PROCESSING_CODE = "SynchronizeStatistics.findByLunoProcessingCode";
    public static final String FIND_BY_LUNO_CARD_NUMBER_PROCESSING_CODE = "SynchronizeStatistics.findByLunoCardNumberProcessingCode";

    @Column(name = "LUNO", nullable = false, length = 10)
    private String luno;

    @Column(name = "CARD_NUMBER", length = 19)
    private String cardNumber;

    @Column(name = "PROCESSING_CODE", nullable = false, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}
