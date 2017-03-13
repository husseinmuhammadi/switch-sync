package com.dpi.financial.ftcom.model.to.isc.transaction;

import com.dpi.financial.ftcom.model.converter.DeviceCodeConverter;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.converter.ProductCodeConverter;
import com.dpi.financial.ftcom.model.converter.isc.transaction.InteractionPointConverter;
import com.dpi.financial.ftcom.model.type.AcquiringInstitutionIdentificationCode;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.ProductCode;
import com.dpi.financial.ftcom.model.type.isc.DeviceCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.InteractionPoint;

import javax.persistence.*;
import java.util.Date;

/**
 * https://en.wikibooks.org/wiki/Java_Persistence/Inheritance
 * FinancialBase represent authorization, financial and reversal transactions
 */
@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SWITCH_TRANSACTION_SEQ")
@Table(name = "SWITCH_TRANSACTION")
@NamedQueries({
        @NamedQuery(name = FinancialBase.FIND_ALL, query = "select t from FinancialBase t where t.deleted = false")
})
public abstract class FinancialBase extends Transaction {
    public static final String FIND_ALL = "FinancialBase.findAll";

    // INTERACTION_MODE
    @Column(name = "INTERACTION_POINT", nullable = true, length = 1)
    @Convert(converter = InteractionPointConverter.class)
    private InteractionPoint interactionPoint;

    @Deprecated
    @Column(name = "FINANCIAL_SERVICE_PROVIDER", length = 1)
    private String financialServiceProvider;

    @Column(name = "PRODUCT_CODE", nullable = true, length = 3)
    @Convert(converter = ProductCodeConverter.class)
    private ProductCode productCode;

    /**
     * length value changed from 3 to 2 as mentioned in SHETAB VOL 2 TRANSACTION DETAILS VERSION 7.0.4 Page 57
     */
    @Column(name = "DEVICE_CODE", nullable = true, length = 2)
    @Convert(converter = DeviceCodeConverter.class)
    private DeviceCode deviceCode;

    // ISO Document
    @Column(name = "PRIMARY_ACCOUNT_NUMBER", nullable = false, length = 19)
    private String pan;

    @Column(name = "PROCESSING_CODE", nullable = false, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSMISSION_DATE", nullable = false)
    private Date transmissionDateTime;

    // SYSTEM TRACE AUDIT NUMBER
    @Column(name = "STAN", nullable = false)
    private Integer stan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOCAL_DATE", nullable = false)
    private Date localDateTime;

    @Column(name = "ACQUIRING_INSTITUTION_CODE")
    private AcquiringInstitutionIdentificationCode acquiringInstitutionIdentificationCode;

    @Column(name = "RRN", nullable = false, length = 12)
    private String retrievalReferenceNumber;

    // P41
    @Column(name = "TERMINAL_ID", nullable = false, length = 8)
    private String cardAcceptorTerminalIdentification;

    // P42
    @Column(name = "CARD_ACCEPTOR_CODE", nullable = false, length = 15)
    private String cardAcceptorIdentificationCode;

    // P53
    @Column(name = "SECURITY_CONTROL_INFO", nullable = false, length = 16)
    private String securityRelatedControlInformation;

    @Column(name = "IBAN", nullable = true, length = 50)
    private String iban;

    // P62 Transaction Enhanced Information
    private TransactionCoding transactionCoding;


    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public void setProductCode(ProductCode productCode) {
        this.productCode = productCode;
    }

    public DeviceCode getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(DeviceCode deviceCode) {
        this.deviceCode = deviceCode;
    }

    public InteractionPoint getInteractionPoint() {
        return interactionPoint;
    }

    public void setInteractionPoint(InteractionPoint interactionPoint) {
        this.interactionPoint = interactionPoint;
    }
}
