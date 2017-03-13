package com.dpi.financial.ftcom.model.type.isc.transaction;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.model.type.YesNoType;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

import javax.print.attribute.standard.NumberUp;

/**
 * Error severity represent message error as mentioned in SHETAB VOL 2 TRANSACTION DETAILS VERSION 7.0.4 Page 57 Table 70
 */
public enum MessageReasonCode implements IEnumFieldValue<String> {
    OPERATION_CANCELED_BY_CARD_HOLDER("4000", OperationState.CANCELED),
    UNKNOWN_OPERATION_NOT_DONE("4001", OperationState.NOT_DONE),
    ACQUIRER_INTERNAL_ERROR("4002"),
    MESSAGE_CONTENT_ERROR("4003", OperationState.NOT_DONE),
    PARTIAL_COMPLETED("4004", OperationState.PARTIAL_COMPLETED),
    INCORRECT_AMOUNT("4005"),
    RESPONSE_RECEIVED_TIME_OUT_EXCEPTION("4006"),
    UNABLE_TO_COMPLETE_TRANSACTION("4007"),
    MESSAGE_NOT_SENT_TO_TERMINAL("4013"),
    ACQUIRER_INTERNAL_ERROR_CARD_CAPTURED("4014", null, YesNoType.Yes),
    ACQUIRER_INTERNAL_ERROR_CARD_NOT_CAPTURED("4015", null, YesNoType.No),
    ACQUIRER_INTERNAL_ERROR_CASH_NOT_TAKEN("4017"), // todo CASH_NOT_TAKEN
    CASH_RETRACTED("4018"), // todo CASH_NOT_TAKEN
    CASH_RETRACTED_CARD_CAPTURED("4019", null, YesNoType.Yes), // todo CASH_NOT_TAKEN
    INVALID_RESPONSE_CODE("4020", OperationState.NOT_DONE),
    TIME_OUT_EXCEPTION("4021");

    private final String messageReasonCode;
    private final OperationState operationState;
    private final YesNoType cardCaptured;

    MessageReasonCode(String messageReasonCode, OperationState operationState, YesNoType cardCaptured) {
        this.messageReasonCode = messageReasonCode;
        this.operationState = operationState;
        this.cardCaptured= cardCaptured;
    }

    MessageReasonCode(String messageReasonCode, OperationState operationState) {
        this(messageReasonCode, operationState, null);
    }

    MessageReasonCode(String messageReasonCode) {
        this(messageReasonCode, null);
    }

    public static MessageReasonCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (MessageReasonCode messageReasonCode : values()) {
            if (messageReasonCode.getValue().equals(value))
                return messageReasonCode;
        }

        throw new TypeNotFoundException(MessageReasonCode.class.getName()
                + " Error creating instance for Message Reason Code: " + value);
    }

    @Override
    public String getValue() {
        return this.messageReasonCode;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
