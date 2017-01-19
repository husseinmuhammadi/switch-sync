package com.dpi.financial.ftcom.model.to.isc.transaction;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.DeviceCodeConverter;
import com.dpi.financial.ftcom.model.converter.FinancialServiceProviderConverter;
import com.dpi.financial.ftcom.model.converter.ProcessingCodeConverter;
import com.dpi.financial.ftcom.model.converter.ProductCodeConverter;
import com.dpi.financial.ftcom.model.type.DeviceCode;
import com.dpi.financial.ftcom.model.type.FinancialServiceProvider;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.ProductCode;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SWITCH_TRANSACTION_SEQ")
@Table(name = "SWITCH_TRANSACTION")
@NamedQueries({
        @NamedQuery(name = SwitchTransaction.FIND_ALL, query = "select t from SwitchTransaction t where t.deleted = false")
})
public class SwitchTransaction extends EntityBase {
    public static final String FIND_ALL = "SwitchTransaction.findAll";

    @Column(name = "FINANCIAL_SERVICE_PROVIDER", nullable = false, length = 1)
    @Convert(converter = FinancialServiceProviderConverter.class)
    private FinancialServiceProvider financialServiceProvider;

    @Column(name = "PROCESSING_CODE", nullable = true, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @Column(name = "PRODUCT_CODE", nullable = true, length = 3)
    @Convert(converter = ProductCodeConverter.class)
    private ProductCode productCode;

    @Column(name = "DEVICE_CODE", nullable = true, length = 3)
    @Convert(converter = DeviceCodeConverter.class)
    private DeviceCode deviceCode;


    public FinancialServiceProvider getFinancialServiceProvider() {
        return financialServiceProvider;
    }

    public void setFinancialServiceProvider(FinancialServiceProvider financialServiceProvider) {
        this.financialServiceProvider = financialServiceProvider;
    }

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
}
