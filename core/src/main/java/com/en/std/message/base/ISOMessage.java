package com.en.std.message.base;

import com.en.std.definition.ProcessingCode;
import com.en.std.message.indicator.MessageTypeIndicator;
import org.jpos.iso.ISOMsg;

import java.util.BitSet;
import java.util.Date;

/**
 * @since ver 2.1.7 modified by Hossein Mohammadi w.r.t Bug #12616 as on Monday, January 04, 2016
 */
public abstract class ISOMessage {

    protected ISOMsg isoMsg;

    private MessageTypeIndicator mti;

    // Data Field 1
    // Type b 64
    private BitSet bitmap;

    // Data Field 2
    // Type n ..19
    private String primaryAccountNumber;
    // private String pan;

    // Data Field 3
    // Type n 6
    protected ProcessingCode processingCode;

    // Data Field 4
    // Type n 12
    private String transactionAmount;

    // Data Field 5
    // Type n 12
    private String settlementAmount;

    // Data Field 6
    // Type n 12
    private String cardholderBillingAmount;

    // Data Field 7
    // Type n 10
    private Date transmissionDateTime;

    // Data Field 8
    // Type n 8
    private String cardholderBillingFeeAmount;

    // Data Field 9
    // Type n 8
    private String settlementConversionRate;

    // Data Field 10
    // Type n 8
    private String cardholderBillingConversionRate;

    // Data Field 11
    // Type n 6
    private long systemTraceAuditNumber;
    // private long stan;

    // Data Field 12
    // Type n 6
    // Format (hhmmss)
    private String localTransactionTime;
    ;
    // Data Field 13
    // Type n 4
    // Format (MMDD)
    private String localTransactionDate;

    // Data Field 14
    // Type n 4
    private String expirationDate;

    // Data Field 15
    // Type n 4
    private String settlementDate;

    // Data Field 16
    // Type n 4
    private String conversionDate;

    // Data Field 17
    // Type n 4
    private String captureDate;

    // Data Field 18
    // Type n 4
    private String Merchant_type;

    // Data Field 19
    // Type n 3
    private String Acquiring_institution_country_code;

    // Data Field 20
    // Type n 3
    private String PAN_extended, _country_code;

    // Data Field 21
    // Type n 3
    private String Forwarding_institution_country_code;

    // Data Field 22
    // Type n 3
    private String Point_of_service_entry_mode;

    // Data Field 23
    // Type n 3
    private String Application_PAN_sequence_number;

    // Data Field 24
    // Type n 3
    // Format (ISO_8583:1993)
    private String Function_code_;
    private String Network_International_identifier_;
    private String nii;

    // Data Field 25
    // Type n 2
    private String pointOfServiceConditionCode;

    // Data Field 26
    // Type n 2
    private String Point_of_service_capture_code;

    // Data Field 27
    // Type n 1
    private String Authorizing_identification_response_length;

    // Data Field 28
    // Type x+n 8
    private String transactionFeeAmount;

    // Data Field 29
    // Type x+n 8
    private String settlementFeeAmount;

    // Data Field 30
    // Type x+n 8
    private String transactionProcessingFeeAmount;

    // Data Field 31
    // Type x+n 8
    private String settlementProcessingFeeAmount;

    // Data Field 32
    // Type n ..11
    private String acquiringInstitutionIdentificationCode;

    // Data Field 33
    // Type n ..11
    private String forwardingInstitutionIdentificationCode;

    // Data Field 34
    // Type ns ..28
    private String primaryAccountNumberExtended;

    // Data Field 35
    // Type z ..37
    private String Track2Data;

    // Data Field 36
    // Type n ...104
    private String Track_3_data;

    // Data Field 37
    // Type an 12
    private String retrievalReferenceNumber;
    // private String rrn;

    // Data Field 38
    // Type an 6
    private String Authorization_identification_response;

    // Data Field 39
    // Type an 2
    private String Response_code;

    // Data Field 40
    // Type an 3
    private String Service_restriction_code;

    // Data Field 41
    // Type ans 8
    private String cardAcceptorTerminalIdentification;

    // Data Field 42
    // Type ans 15
    private String cardAcceptorIdentificationCode;

    // Data Field 43
    // Type ans 40
    // Format (1-23_address_24-36_city_37-38_state_39-40_country)
    private String cardAcceptorNameLocation;

    // Data Field 44
    // Type an ..25
    private String additionalResponseData;

    // Data Field 45
    // Type an ..76
    private String Track_1_data;

    // Data Field 46
    // Type an ...999
    private String additionalDataISO;

    // Data Field 47
    // Type an ...999
    private String additionalDataNational;

    // Data Field 48
    // Type an ...999
    private String additionalDataPrivate;

    // Data Field 49
    // Type a or n 3
    private String transactionCurrencyCode;

    // Data Field 50
    // Type a or n 3
    private String settlementCurrencyCode;

    // Data Field 51
    // Type a or n 3
    private String cardholderBillingCurrencyCode;

    // Data Field 52
    // Type b 64
    private byte[] personalIdentificationNumberData;

    // Data Field 53
    // Type n 16
    private String securityRelatedControlInformation;

    // Data Field 54
    // Type an ...120
    private String Additional_amounts;

    // Data Field 55
    // Type ans ...999
    private String reservedISODataField55;

    // Data Field 56
    // Type ans ...999
    private String reservedISODataField56;

    // Data Field 57
    // Type ans ...999
    private String reservedNationalDataField57;

    // Data Field 58
    // Type ans ...999
    private String reservedNationalDataField58;

    // Data Field 59
    // Type ans ...999
    private String reservedNationalDataField59;

    // Data Field 60
    // Type ans ...999
    private String reservedNationalDataField60;

    // Data Field 61
    // Type ans ...999
    private String reservedPrivateDataField61;

    // Data Field 62
    // Type ans ...999
    private String reservedPrivateDataField62;

    // Data Field 63
    // Type ans ...999
    private String Reserved_private;

    // Data Field 64
    // Type b 16
    private String messageAuthenticationCode64;
    // private String mac;

    // Data Field 65
    // Type b 1
    private String extendedBitmap;

    // Data Field 66
    // Type n 1
    private String Settlement_code;

    // Data Field 67
    // Type n 2
    private String Extended_payment_code;

    // Data Field 68
    // Type n 3
    private String Receiving_institution_country_code;

    // Data Field 69
    // Type n 3
    private String Settlement_institution_country_code;

    // Data Field 70
    // Type n 3
    private String networkManagementInformationCode;
    // private String nic;

    // Data Field 71
    // Type n 4
    private String messageNumber;

    // Data Field 72
    // Type n 4
    private String lastMessageNumber;

    // Data Field 73
    // Type n 6
    // Format (YYMMDD)
    private String actionDate;

    // Data Field 74
    // Type n 10
    private String creditsNumber;

    // Data Field 75
    // Type n 10
    private String creditsReversalNumber;

    // Data Field 76
    // Type n 10
    private String debitsNumber;

    // Data Field 77
    // Type n 10
    private String debitsReversalNumber;

    // Data Field 78
    // Type n 10
    private String transferNumber;

    // Data Field 79
    // Type n 10
    private String transferReversalNumber;

    // Data Field 80
    // Type n 10
    private String Inquiries_number;

    // Data Field 81
    // Type n 10
    private String suthorizationsNumber;

    // Data Field 82
    // Type n 12
    private String creditsProcessingFeeAmount;

    // Data Field 83
    // Type n 12
    private String creditsTransactionFeeAmount;

    // Data Field 84
    // Type n 12
    private String debitsProcessingFeeAmount;

    // Data Field 85
    // Type n 12
    private String debitsTransactionFeeAmount;

    // Data Field 86
    // Type n 16
    private String creditsAmount;

    // Data Field 87
    // Type n 16
    private String creditsReversalAmount;

    // Data Field 88
    // Type n 16
    private String debitsAmount;

    // Data Field 89
    // Type n 16
    private String debitsReversalAmount;

    // Data Field 90
    // Type n 42
    private String originalDataElements;

    // Data Field 91
    // Type an 1
    private String File_update_code;

    // Data Field 92
    // Type an 2
    private String File_security_code;

    // Data Field 93
    // Type an 5
    private String Response_indicator;

    // Data Field 94
    // Type an 7
    private String Service_indicator;

    // Data Field 95
    // Type an 42
    private String replacementAmounts;

    // Data Field 96
    // Type b 64
    private byte[] messageSecurityCode;

    // Data Field 97
    // Type x+n 16
    private String Amount, _net_settlement;

    // Data Field 98
    // Type ans 25
    private String Payee;

    // Data Field 99
    // Type n ..11
    private String Settlement_institution_identification_code;

    // Data Field 100
    // Type n ..11
    private String receivingInstitutionIdentificationCode;
    // private String riid;

    // Data Field 101
    // Type ans ..17
    private String File_name;

    // Data Field 102
    // Type ans ..28
    private String Account_identification_1;

    // Data Field 103
    // Type ans ..28
    private String Account_identification_2;

    // Data Field 104
    // Type ans ...100
    private String Transaction_description;

    // Data Field 105
    // Type ans ...999
    private String reservedForISOUseDataField105;

    // Data Field 106
    // Type ans ...999
    private String reservedForISOUseDataField106;

    // Data Field 107
    // Type ans ...999
    private String reservedForISOUseDataField107;

    // Data Field 108
    // Type ans ...999
    private String reservedForISOUseDataField108;

    // Data Field 109
    // Type ans ...999
    private String reservedForISOUseDataField109;

    // Data Field 110
    // Type ans ...999
    private String reservedForISOUseDataField110;

    // Data Field 111
    // Type ans ...999
    private String reservedForISOUseDataField112;

    // Data Field 112
    // Type ans ...999
    private String reservedForNationalUseDataField112;

    // Data Field 113
    // Type ans ...999
    private String reservedForNationalUseDataField113;

    // Data Field 114
    // Type ans ...999
    private String reservedForNationalUseDataField114;

    // Data Field 115
    // Type ans ...999
    private String reservedForNationalUseDataField115;

    // Data Field 116
    // Type ans ...999
    private String reservedForNationalUseDataField116;

    // Data Field 117
    // Type ans ...999
    private String reservedForNationalUseDataField117;

    // Data Field 118
    // Type ans ...999
    private String reservedForNationalUseDataField118;

    // Data Field 119
    // Type ans ...999
    private String reservedForNationalUseDataField119;

    // Data Field 120
    // Type ans ...999
    private String reservedForPrivateUseDataField120;

    // Data Field 121
    // Type ans ...999
    private String reservedForPrivateUseDataField121;

    // Data Field 122
    // Type ans ...999
    private String reservedForPrivateUseDataField122;

    // Data Field 123
    // Type ans ...999
    private String reservedForPrivateUseDataField123;

    // Data Field 124
    // Type ans ...999
    private String reservedForPrivateUseDataField124;

    // Data Field 125
    // Type ans ...999
    private String reservedForPrivateUseDataField125;

    // Data Field 126
    // Type ans ...999
    private String reservedForPrivateUseDataField126;

    // Data Field 127
    // Type ans ...999
    private String reservedForPrivateUseDataField127;

    // Data Field 128
    // Type b 64
    private byte[] messageAuthenticationCode128;

    public static boolean isReversal(String mti) {
        try {
            String reverse = new StringBuffer(mti).reverse().toString();
            String items[] = reverse.split("(?!^)");
            if (items[2] != null && items[2].equals("4")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
