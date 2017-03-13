package com.dpi.financial.ftcom.model.to.isc.transaction;

import com.dpi.financial.ftcom.model.converter.isc.transaction.ErrorCodeConverter;
import com.dpi.financial.ftcom.model.converter.isc.transaction.ErrorSeverityConverter;
import com.dpi.financial.ftcom.model.type.isc.transaction.ErrorCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.ErrorSeverity;

import javax.persistence.Column;
import javax.persistence.Convert;

/**
 * Created by h.mohammadi on 3/11/2017.
 */

public class MessageErrorIndicator {

    @Column(name = "ERROR_SEVERITY", nullable = false, length = 2)
    @Convert(converter = ErrorSeverityConverter.class)
    private ErrorSeverity errorSeverity;

    @Column(name = "ERROR_CODE", nullable = false, length = 4)
    @Convert(converter = ErrorCodeConverter.class)
    private ErrorCode errorCode;

    @Column(name = "FIELD_NO", nullable = false)
    private Integer fieldNo;

    @Column(name = "FIELD_ITEM_NO", nullable = false)
    private Integer fieldItemNo;
}
