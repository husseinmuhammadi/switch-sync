package com.dpi.financial.ftcom.model.type.isc.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Error severity represent message error as mentioned in SHETAB VOL 2 TRANSACTION DETAILS VERSION 7.0.4 Page 57 Table 70
 */
public enum ErrorCode implements IEnumFieldValue<String> {
    DATA_NOT_FOUND("0001"),
    INVALID_LENGTH("0002"),
    INVALID_DATA("0003"),
    INCORRECT_AMOUNT_FORMAT("0004"),
    INCORRECT_DATE_FORMAT("0005"),
    INCORRECT_ACCOUNT_NUMBER_FORMAT("0006"),
    INCORRECT_NAME_FORMAT("0007"),
    INCORRECT_MESSAGE_FORMAT("0008"),
    INCONSISTENT_DATA_BASED_ON_POSCC("0009"),
    INCONSISTENT_DATA_BASED_ON_ORIGIN_TRANSACTION("0010"),
    INCONSISTENT_DATA("0011"),
    TIME_OUT_EXCEPTION("0012"),
    DATA_CONTENT_ERROR("0013"),
    ;

    private String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public static ErrorCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (ErrorCode errorCode : values()) {
            if (errorCode.getValue().equals(value))
                return errorCode;
        }

        throw new TypeNotFoundException(ErrorCode.class.getName()
                + " Error creating instance for Error Code: " + value);
    }

    @Override
    public String getValue() {
        return this.errorCode;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
