package com.dpi.financial.ftcom.model.to.atm.ndc;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.OperationStateConverter;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.converter.YesNoTypeConverter;
import com.dpi.financial.ftcom.model.converter.atm.ndc.LanguageConverter;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.model.type.atm.ndc.Language;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NDC_OPERATION_CODE_SEQ")
@Table(name = "NDC_OPERATION_CODE", uniqueConstraints = {
        // @UniqueConstraint(columnNames = {"OPERATION_CODE_BUFFER", "EFFECTIVE_DATE"}),
})
@NamedQueries({
        @NamedQuery(name = OperationCode.FIND_ALL, query = "select t from OperationCode t where t.deleted = false"),
        @NamedQuery(name = OperationCode.FIND_ALL_BY_EFFECTIVE_DATE, query = "select t from OperationCode t where t.deleted = false and t.effectiveDate >= :effectiveDate"),
})
public class OperationCode extends EntityBase {
    public static final String FIND_ALL = "OperationCode.findAll";
    public static final String FIND_ALL_BY_EFFECTIVE_DATE = "OperationCode.findAllByEffectiveDate";

    @Column(name = "PROCESSING_CODE", nullable = false, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 5)
    private String transactionType;

    @Column(name = "TRANSACTION_DESCRIPTION", nullable = false, length = 100)
    private String transactionDescription;

    @Column(name = "OPERATION_CODE_BUFFER", nullable = false, length = 8)
    private String operationCodeBuffer;

    @Column(name = "FROM_ACCOUNT", nullable = false, length = 2)
    private String fromAccount;

    @Column(name = "TO_ACCOUNT", nullable = false, length = 2)
    private String toAccount;

    @Column(name = "LANGUAGE", nullable = false, length = 2)
    @Convert(converter = LanguageConverter.class)
    private Language language;

    @Column(name = "PRINT_RECEIPT", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType printReceipt;

    @Column(name = "OTHER_ACCOUNT", length = 1)
    @Convert(converter = YesNoTypeConverter.class)
    private YesNoType otherAccount;

    @Column(name = "SELECTED_ACCOUNT_NO")
    private Long selectedAccountNo;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_DATE", nullable = false)
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_TIME", nullable = false)
    private Date effectiveTime;

    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getOperationCodeBuffer() {
        return operationCodeBuffer;
    }

    public void setOperationCodeBuffer(String operationCodeBuffer) {
        this.operationCodeBuffer = operationCodeBuffer;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public YesNoType getPrintReceipt() {
        return printReceipt;
    }

    public void setPrintReceipt(YesNoType printReceipt) {
        this.printReceipt = printReceipt;
    }

    public YesNoType getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(YesNoType otherAccount) {
        this.otherAccount = otherAccount;
    }

    public Long getSelectedAccountNo() {
        return selectedAccountNo;
    }

    public void setSelectedAccountNo(Long selectedAccountNo) {
        this.selectedAccountNo = selectedAccountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}