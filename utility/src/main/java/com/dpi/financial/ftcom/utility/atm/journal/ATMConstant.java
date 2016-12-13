package com.dpi.financial.ftcom.utility.atm.journal;

/**
 * Created by h.mohammadi on 12/6/2016.
 */
public class ATMConstant {
    public static final String ATM_PROP_OPERATION_CODE = "OPCODE";
    public static final String ATM_PROP_TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static final String ATM_PROP_TRANSACTION_DESCRIPTION = "TRANSACTION_DESCRIPTION";
    public static final String ATM_PROP_ID = "ID";
    public static final String ATM_PROP_NAME = "NAME";
    public static final String ATM_PROP_PROCESSING_CODE = "PCODE";
    public static final String ATM_PROP_OPERATION_CODE_BUFFER = "VALUE";
    public static final String ATM_PROP_FROM_ACCOUNT = "FROM_ACCOUNT";
    public static final String ATM_PROP_TO_ACCOUNT = "TO_ACCOUNT";
    public static final String ATM_PROP_LANG = "LANG";
    public static final String ATM_PROP_AMOUNT = "AMOUNT";
    public static final String ATM_PROP_FAST_CASH_AMOUNT = "FAST_CASH_AMOUNT";
    public static final String ATM_PROP_PRINT_RECEIPT = "PRINT_RECEIPT";
    public static final String ATM_PROP_OTHER_ACCOUNT = "OTHER_ACCOUNT";
    public static final String ATM_PROP_SELECTED_ACCOUNT_NO = "SELECTED_ACCOUNT_NO";

    public static final String ATM_PROP_CASH_NOTE_NO_FROM_CASSETTE_ONE = "CASH_NOTE_NO_FROM_CASSETTE_ONE_PROP";
    public static final String ATM_REGEX_CASH_NOTE_FROM_CASSETTE_ONE = "1:1,\\d+;";

    public static final String ATM_PROP_CASH_NOTE_NO_FROM_CASSETTE_TWO = "CASH_NOTE_NO_FROM_CASSETTE_TWO_PROP";
    public static final String ATM_REGEX_CASH_NOTE_FROM_CASSETTE_TWO = "2:2,\\d+;";

    public static final String ATM_PROP_CASH_NOTE_NO_FROM_CASSETTE_THREE = "CASH_NOTE_NO_FROM_CASSETTE_THREE_PROP";
    public static final String ATM_REGEX_CASH_NOTE_FROM_CASSETTE_THREE = "3:3,\\d+;";

    public static final String ATM_PROP_CASH_NOTE_NO_FROM_CASSETTE_FOUR = "CASH_NOTE_NO_FROM_CASSETTE_FOUR_PROP";
    public static final String ATM_REGEX_CASH_NOTE_FROM_CASSETTE_FOUR = "4:4,\\d+;";

    public static final String ATM_STATE_TRANSACTION_START = "TRANSACTION START";
    public static final String ATM_REGEX_TRANSACTION_START = "TRANSACTION START";

    public static final String ATM_STATE_TRACK_DATA = "TRACK DATA";
    public static final String ATM_REGEX_TRACK_DATA = "TRACK\\s+\\d\\s+DATA:\\s+";

    public static final String ATM_STATE_TRACK_1_DATA = "TRACK 1 DATA";
    public static final String ATM_REGEX_TRACK_1_DATA = "TRACK\\s+1\\s+DATA:\\s+";

    public static final String ATM_STATE_TRACK_2_DATA = "TRACK 2 DATA";
    public static final String ATM_REGEX_TRACK_2_DATA = "TRACK\\s+2\\s+DATA:\\s+";

    public static final String ATM_STATE_TRACK_3_DATA = "TRACK 3 DATA";
    public static final String ATM_REGEX_TRACK_3_DATA = "TRACK\\s+3\\s+DATA:\\s+";

    public static final String ATM_STATE_AMOUNT_ENTERED = "AMOUNT ENTERED";
    // public static final String ATM_REGEX_AMOUNT_ENTERED = "AMOUNT\\s+\\d*\\s+ENTERED";
    public static final String ATM_REGEX_AMOUNT_ENTERED = "AMOUNT\\s+\\p{Alnum}*\\s+ENTERED";
    // public static final String ATM_REGEX_AMOUNT_ENTERED_VALUE = "\\s+\\d*\\s+";
    public static final String ATM_REGEX_AMOUNT_ENTERED_VALUE = "\\s+\\p{Alnum}*\\s+";

    public static final String ATM_STATE_INFORMATION_ENTERED = "INFORMATION ENTERED";
    // Standard edition id commented for unexpected forms
    // public static final String ATM_REGEX_INFORMATION_ENTERED = "INFORMATION\\s+\\d*\\s+ENTERED";
    // public static final String ATM_REGEX_INFORMATION_ENTERED = "INFORMATION\\s+[\\p{Alpha}\\p{Digit}]*\\s+ENTERED";
    public static final String ATM_REGEX_INFORMATION_ENTERED = "INFORMATION\\s+\\p{Alnum}*\\s+ENTERED";
    // public static final String ATM_REGEX_INFORMATION_ENTERED_VALUE = "\\s+\\d*\\s+";
    public static final String ATM_REGEX_INFORMATION_ENTERED_VALUE = "\\s+\\p{Alnum}*\\s+";

    public static final String ATM_STATE_TRANSACTION_FAILED = "TRANSACTION FAILED";
    public static final String ATM_REGEX_TRANSACTION_FAILED = "TRANSACTION\\s+\\d+\\s+FAILED";

    public static final String ATM_STATE_PIN_ENTERED = "PIN ENTERED";
    public static final String ATM_REGEX_PIN_ENTERED = "PIN ENTERED";

    public static final String ATM_STATE_TRANSACTION_REQUEST = "TRANSACTION REQUEST";
    public static final String ATM_STATE_TRANSACTION_REQUEST_NEXT = "TRANSACTION REQUEST NEXT";
    public static final String ATM_REGEX_TRANSACTION_REQUEST = "TRANSACTION\\s+REQUEST\\s+";

    public static final String ATM_STATE_CARD_TAKEN = "CARD TAKEN";
    public static final String ATM_REGEX_CARD_TAKEN = "CARD TAKEN";

    public static final String ATM_STATE_TRANSACTION_REPLY = "TRANSACTION REPLY";
    public static final String ATM_REGEX_TRANSACTION_REPLY = "TRANSACTION REPLY ";

    public static final String ATM_STATE_CASH_REQUEST = "CASH REQUEST";
    public static final String ATM_REGEX_CASH_REQUEST = "CASH\\s+REQUEST:\\s+";

    public static final String ATM_STATE_CASH = "CASH";
    public static final String ATM_REGEX_CASH = "CASH\\s+(\\d:\\d,\\d+;)+";
    public static final String ATM_REGEX_CASH_VALUE = "\\s+(\\d:\\d,\\d+;)*";

    public static final String ATM_STATE_ATM_STATUS_MESSAGE = "STATUS MESSAGE";
    public static final String ATM_REGEX_CDM_ERROR = "CDM\\s+ERROR:\\s+";
    public static final String ATM_REGEX_RECEIVED_INVALID_MESSAGE_COORDINATION_NUMBER = "RECEIVED\\s+INVALID\\s+MESSAGE\\s+COORDINATION\\s+NUMBER";
    public static final String ATM_REGEX_COMMUNICATION_ERROR = "COMMUNICATION\\s+ERROR";
    public static final String ATM_REGEX_COMMUNICATION_OFFLINE = "COMMUNICATION\\s+OFFLINE";
    public static final String ATM_REGEX_JRN_REC_ERROR = "JRN/REC\\s+ERROR:\\s+";
    public static final String ATM_REGEX_LOGICAL_CASSETTE_LOW = "LOGICAL\\s+CASSETTE\\s+\\d\\s+LOW";
    public static final String ATM_REGEX_PHYSICAL_CASSETTE_LOW = "PHYSICAL\\s+CASSETTE\\s+\\d\\s+LOW";
    public static final String ATM_REGEX_LOGICAL_CASSETTE_EMPTY = "LOGICAL\\s+CASSETTE\\s+\\d\\s+EMPTY";
    public static final String ATM_REGEX_PHYSICAL_CASSETTE_EMPTY = "PHYSICAL\\s+CASSETTE\\s+\\d\\s+EMPTY";
    public static final String ATM_REGEX_GO_OUT_OF_SERVICE_COMMAND = "GO\\s+OUT\\s+OF\\s+SERVICE\\s+COMMAND";
    public static final String ATM_REGEX_SAFE_DOOR_OPENED = "SAFE\\s+DOOR\\s+OPENED";
    public static final String ATM_REGEX_REJECT_CASSETTE_REMOVED = "REJECT\\s+CASSETTE\\s+REMOVED";
    public static final String ATM_REGEX_CARD_RETAINED = "CARD\\s+RETAINED";
    public static final String ATM_REGEX_CASH_RETRACTED = "CASH\\s+RETRACTED";
    public static final String ATM_REGEX_CARD_JAMMED = "CARD\\s+JAMMED";
    public static final String ATM_REGEX_SAFE_DOOR_CLOSED = "SAFE\\s+DOOR\\s+CLOSED";
    public static final String ATM_REGEX_IDCU_ERROR = "IDCU\\s+ERROR";

    public static final String ATM_STATE_CASH_PRESENTED = "CASH PRESENTED";
    public static final String ATM_REGEX_CASH_PRESENTED = "CASH PRESENTED";

    public static final String ATM_STATE_CASH_WITHDRAWAL = "CASH WITHDRAWAL";
    public static final String ATM_REGEX_CASH_WITHDRAWAL = "^\\s*CASH\\s+WITHDRAWAL";
    public static final String ATM_REGEX_CASH_WITHDRAWAL_VALUE = "CASH\\s+WITHDRAWAL";
    public static final String ATM_REGEX_SWITCH_DATE_TIME_LUNO = "\\d\\d/\\d\\d/\\d\\d\\d\\d\\s+\\d\\d:\\d\\d:\\d\\d\\s+\\d+";
    public static final String ATM_PROP_SWITCH_DATE_TIME = "SWITCH_DATE_TIME";
    public static final String ATM_REGEX_SWITCH_DATE_TIME = "\\d\\d/\\d\\d/\\d\\d\\d\\d\\s+\\d\\d:\\d\\d:\\d\\d";
    public static final String ATM_PROP_LUNO = "LUNO";

    public static final String ATM_REGEX_BALANCE_INQUIRY = "^\\s*BALANCE\\s+INQUIRY";

    public static final String ATM_STATE_CARD_NO = "CARD_NO";
    public static final String ATM_REGEX_CARD_NO = "CARD\\s+NO\\s+:\\s+";

    public static final String ATM_STATE_RRN = "RRN";
    public static final String ATM_REGEX_RRN = "R.R.N.\\s+:\\s+";

    public static final String ATM_STATE_STAN = "STAN";
    public static final String ATM_REGEX_STAN = "STAN\\s+:\\s+";

    public static final String ATM_STATE_AMOUNT = "AMOUNT";
    public static final String ATM_REGEX_AMOUNT = "AMOUNT\\s+:\\s+";

    public static final String ATM_STATE_RESPONSE_CODE = "RESPONSE CODE";
    public static final String ATM_REGEX_RESPONSE_CODE = "RESPONSE CODE : ";

    public static final String ATM_STATE_RESPONSE = "RESPONSE";
    public static final String ATM_REGEX_RESPONSE = "RESPONSE\\s+:\\s+";

    public static final String ATM_STATE_SUCCESSFUL = "SUCCESSFUL";
    public static final String ATM_REGEX_SUCCESSFUL = "^\\s*SUCCESSFUL";

    public static final String ATM_STATE_UNSUCCESSFUL = "UNSUCCESSFUL";
    public static final String ATM_REGEX_UNSUCCESSFUL = "^\\s*UNSUCCESSFUL";

    public static final String ATM_STATE_CASH_TAKEN = "CASH TAKEN";
    public static final String ATM_REGEX_CASH_TAKEN = "CASH TAKEN";

    public static final String ATM_STATE_TRANSACTION_END = "TRANSACTION END";
    public static final String ATM_REGEX_TRANSACTION_END = "TRANSACTION\\s+END";

    public static final String ATM_STATE_TRANSACTION_ANY = "TRANSACTION ANY";
    public static final String ATM_REGEX_TRANSACTION_ANY = "\\w*";

    public static final String ATM_REGEX_RECEIVED_MESSAGE_IN_WRONG_MODE = "RECEIVED\\s+MESSAGE\\s+IN\\s+WRONG\\s+MODE";
    public static final String ATM_REGEX_SECOND_CASSETTE_REMOVED = "SECOND\\s+CASSETTE\\s+REMOVED";
    public static final String ATM_REGEX_THIRD_CASSETTE_REMOVED = "THIRD\\s+CASSETTE\\s+REMOVED";
    public static final String ATM_REGEX_REJECT_CASSETTE_INSERTED = "REJECT\\s+CASSETTE\\s+INSERTED";
    public static final String ATM_REGEX_THIRD_CASSETTE_INSERTED = "THIRD\\s+CASSETTE\\s+INSERTED";
    public static final String ATM_REGEX_SECOND_CASSETTE_INSERTED = "SECOND\\s+CASSETTE\\s+INSERTED";
    public static final String ATM_REGEX_BOTTOM_CASSETTE_REMOVED = "BOTTOM\\s+CASSETTE\\s+REMOVED";
    public static final String ATM_REGEX_CASSETTE_REMOVED = "CASSETTE\\s+\\d\\s+REMOVED";
    public static final String ATM_REGEX_CASSETTE_INSERTED = "CASSETTE\\s+\\d\\s+INSERTED";
    public static final String ATM_REGEX_OPERATOR_DOOR_CLOSED = "OPERATOR\\s+DOOR\\s+CLOSED";
    public static final String ATM_REGEX_OPERATOR_DOOR_OPENED = "OPERATOR\\s+DOOR\\s+OPENED";
    public static final String ATM_REGEX_REC_ERROR = "REC\\s+ERROR:\\s+";
    public static final String ATM_REGEX_EDM_ERROR = "EDM\\s+ERROR";
    public static final String ATM_REGEX_CHECK_KEYS = "CHECK\\s+KEYS";
    public static final String ATM_REGEX_GO_IN_SERVICE_COMMAND = "GO\\s+IN\\s+SERVICE\\s+COMMAND";
    public static final String ATM_REGEX_TOP_CASSETTE_INSERTED = "TOP\\s+CASSETTE\\s+INSERTED";
    public static final String ATM_REGEX_BOTTOM_CASSETTE_INSERTED = "BOTTOM\\s+CASSETTE\\s+INSERTED";

    public static final String ATM_PROP_TERMINAL_TRANSACTION_TIME = "TERMINAL TRANSACTION TIME";
    public static final String ATM_REGEX_TERMINAL_TRANSACTION_TIME = "^\\d\\d:\\d\\d:\\d\\d";
    public static final String ATM_PROP_LINE_INDEX = "LINE_INDEX";
    public static final String ATM_PROP_COMMUNICATION_ERROR = "COMMUNICATION ERROR";
    public static final String ATM_PROP_COMMUNICATION_OFFLINE = "COMMUNICATION OFFLINE";
    public static final String ATM_PROP_OFFLINE_COMMUNICATION = "OFFLINE COMMUNICATION";
    public static final String ATM_PROP_CARD_NUMBER = "CARD NUMBER";
    public static final String ATM_PROP_CDM_ERROR = "CDM ERROR";
    public static final String ATM_PROP_LOGICAL_CASSETTE_EMPTY = "LOGICAL CASSETTE EMPTY";
    public static final String ATM_PROP_PHYSICAL_CASSETTE_EMPTY = "PHYSICAL CASSETTE EMPTY";
    public static final String ATM_PROP_CARD_RETAINED = "CARD_RETAINED";
    public static final String ATM_PROP_IDCU_ERROR = "IDCU ERROR";
    public static final String ATM_PROP_CASH_RETRACTED = "CASH_RETRACTED";

    public static final String ATM_REGEX_TERMINAL_MESSAGE = "^\\*";
    public static final String ATM_REGEX_PUNCTUATION = "\\p{Punct}";
    public static final String ATM_REGEX_IGNORE = "^\\p{Punct}+";
    public static final String ATM_REGEX_APPLICATION_STARTED = "APPLICATION STARTED";

    // BOTTOM CASSETTE INSERTED
    // TOP CASSETTE INSERTED
}
