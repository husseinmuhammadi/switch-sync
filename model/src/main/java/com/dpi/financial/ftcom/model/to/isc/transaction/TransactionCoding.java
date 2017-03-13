package com.dpi.financial.ftcom.model.to.isc.transaction;

import com.dpi.financial.ftcom.model.converter.DeviceCodeConverter;
import com.dpi.financial.ftcom.model.to.isc.transaction.security.SecurityCharacteristic;
import com.dpi.financial.ftcom.model.type.isc.DeviceCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.FunctionCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.MessageReasonCode;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.util.List;

/**
 * Represent implementation for bit 62
 */
public class TransactionCoding {
    @Column(name = "TERMINAL_TYPE_CODE", nullable = false, length = 2)
    @Convert(converter = DeviceCodeConverter.class)
    private DeviceCode terminalTypeCode;

    private List<MessageErrorIndicator> messageErrorIndicators;

    private MessageReasonCode messageReasonCode;

    private FunctionCode functionCode;

    private SecurityCharacteristic securityCharacteristic;

    @Column(name = "SHETAB_CONTROLLING_DATA", nullable = false, length = 8)
    private String shetabControllingData;

    private TransactionSupplementaryData transactionSupplementaryData;

    private CardAcceptorSupplementaryData cardAcceptorSupplementaryData;

    @Column(name = "FIRST_PAN", nullable = false, length = 32)
    private String firstPrimaryAccountNumber;

    @Column(name = "SECOND_PAN", nullable = false, length = 32)
    private String secondPrimaryAccountNumber;
}
