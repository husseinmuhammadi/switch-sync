package com.dpi.financial.ftcom.utility.atm.journal;

import com.dpi.financial.ftcom.utility.convert.Convert;
import com.dpi.financial.ftcom.utility.exception.MultipleMatchException;
import com.dpi.financial.ftcom.utility.regex.RegexMatches;
import com.ibm.icu.util.Measure;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.MatchResult;

/**
 * Created by h.mohammadi on 12/12/2016.
 */
public class JournalContent {
    public static String getTransactionReply(String line) throws MultipleMatchException {
        MatchResult result = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REPLY, line);
        if (result != null)
            return line.substring(result.end());
        return null;
    }

    public static String getTime(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TERMINAL_TRANSACTION_TIME, line);
        if (match != null)
            value = line.substring(match.start(), match.end());

        return value.trim();
    }

    public static String getTrackData(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_1_DATA, line);
        if (match != null)
            value = line.substring(match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_2_DATA, line);
        if (match != null)
            value = line.substring(match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_3_DATA, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    public static String getTransactionRequest(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRANSACTION_REQUEST, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    public static String getRrn(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RRN, line);
        if (match != null)
            value = line.substring(match.end());

        return value;
    }

    public static boolean isPinEntered(String line) throws MultipleMatchException {
        boolean value = false;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_PIN_ENTERED, line);
        if (match != null)
            value = true;

        return value;
    }

    public static String getStateTrackData(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_1_DATA, line);
        if (match != null)
            value = ATMConstant.ATM_STATE_TRACK_1_DATA;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_2_DATA, line);
        if (match != null)
            value = ATMConstant.ATM_STATE_TRACK_2_DATA;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TRACK_3_DATA, line);
        if (match != null)
            value = ATMConstant.ATM_STATE_TRACK_3_DATA;

        return value;
    }

    public static String getInformationEntered(String line) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_INFORMATION_ENTERED, line);

        String value = null;
        if (match != null)
            value = line.substring(match.start(), match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_INFORMATION_ENTERED_VALUE, value);
        if (match != null)
            value = value.substring(match.start(), match.end());

        return value.trim();
    }

    public static String getAmountEntered(String line) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED, line);

        String value = null;
        if (match != null)
            value = line.substring(match.start(), match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT_ENTERED_VALUE, value);
        if (match != null)
            value = value.substring(match.start(), match.end());

        return value.trim();
    }

    public static boolean isCardTaken(String line) throws MultipleMatchException {
        boolean value = false;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_TAKEN, line);
        if (match != null)
            value = true;

        return value;
    }

    public static boolean isCashPresented(String line) throws MultipleMatchException {
        boolean value = false;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_PRESENTED, line);
        if (match != null)
            value = true;

        return value;
    }

    public static String getCashRequest(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_REQUEST, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    public static String getCash(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH, line);
        if (match != null)
            value = line.substring(match.start(), match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_VALUE, value);
        if (match != null)
            value = value.substring(match.start(), match.end());

        return value.trim();
    }

    public static Long getCashNoteNoFromCassetteOne(String cashNoteDtls) throws MultipleMatchException {
        MatchResult match;

        String value = null;
        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_NOTE_FROM_CASSETTE_ONE, cashNoteDtls);
        if (match != null)
            value = cashNoteDtls.substring(match.start(), match.end());

        if (value != null)
            value = value.substring("1:1,".length(), value.indexOf(";"));

        if (value != null)
            return Long.parseLong(value);

        return null;
    }

    public static Long getCashNoteNoFromCassetteTwo(String cashNoteDtls) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_NOTE_FROM_CASSETTE_TWO, cashNoteDtls);

        String value = null;
        if (match != null)
            value = cashNoteDtls.substring(match.start(), match.end());

        if (value != null)
            value = value.substring("2:2,".length(), value.indexOf(";"));

        if (value != null)
            return Long.parseLong(value);

        return null;
    }

    public static Long getCashNoteNoFromCassetteThree(String cashNoteDtls) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_NOTE_FROM_CASSETTE_THREE, cashNoteDtls);

        String value = null;
        if (match != null)
            value = cashNoteDtls.substring(match.start(), match.end());

        if (value != null)
            value = value.substring("3:3,".length(), value.indexOf(";"));

        if (value != null)
            return Long.parseLong(value);

        return null;
    }

    public static Long getCashNoteNoFromCassetteFour(String cashNoteDtls) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_NOTE_FROM_CASSETTE_FOUR, cashNoteDtls);

        String value = null;
        if (match != null)
            value = cashNoteDtls.substring(match.start(), match.end());

        if (value != null)
            value = value.substring("4:4,".length(), value.indexOf(";"));

        if (value != null)
            return Long.parseLong(value);

        return null;
    }

    public static String getResponse(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RESPONSE, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    public static String getResponseCode(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_RESPONSE_CODE, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    public static boolean isSuccessful(String line) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SUCCESSFUL, line);

        boolean value = false;
        if (match != null)
            value = true;

        return value;
    }

    public static boolean isUnsuccessful(String line) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_UNSUCCESSFUL, line);

        boolean value = false;
        if (match != null)
            value = true;

        return value;
    }

    public static String getSuccessMessage(String line) throws MultipleMatchException {
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SUCCESSFUL, line);
        if (match!= null)
            return line.substring(match.start(), match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_UNSUCCESSFUL, line);
        if (match!= null)
            return line.substring(match.start(), match.end());

        return null;
    }

    public static boolean isCashTaken(String line) throws MultipleMatchException {
        boolean value = false;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_TAKEN, line);
        if (match != null)
            value = true;

        return value;
    }

    public static Date getSwitchDateTime(String line) throws MultipleMatchException, ParseException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SWITCH_DATE_TIME, line);

        String value = null;
        if (match != null)
            value = line.substring(match.start(), match.end());

        DateFormat format; // = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date switchDateTime = null;

        if (value != null && !value.isEmpty())
            switchDateTime = format.parse(value);

        return switchDateTime;
    }

    public static String getTreminalId(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_SWITCH_DATE_TIME, line);
        if (match != null)
            value = line.substring(match.end());

        return value.trim();
    }

    public static String getTerminalTransactionTime(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_TERMINAL_TRANSACTION_TIME, line);
        if (match != null)
            value = line.substring(match.start(), match.end());

        return value.trim();
    }

    public static String getCashWithdrawal(String line) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_WITHDRAWAL, line);

        String value = null;
        if (match != null)
            value = line.substring(match.start(), match.end());

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CASH_WITHDRAWAL_VALUE, value);
        if (match != null)
            value = value.substring(match.start(), match.end());

        return value;
    }

    public static String getCardNo(String line) throws MultipleMatchException {
        String value = null;
        MatchResult match;

        match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_CARD_NO, line);
        if (match != null)
            value = line.substring(match.end());

        return value;
    }

    public static Integer getStan(String line) throws MultipleMatchException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_STAN, line);

        String value = null;
        if (match != null)
            value = line.substring(match.end());

        Integer stan = null;

        if (value != null && !value.isEmpty())
            stan = Integer.parseInt(value);

        return stan;
    }

    public static BigDecimal getCashAmount(String line) throws MultipleMatchException, ParseException {
        MatchResult match = RegexMatches.getSingleResult(ATMConstant.ATM_REGEX_AMOUNT, line);

        String value = null;
        if (match != null)
            value = line.substring(match.end());

        BigDecimal amount = null;

        if (value != null && !value.isEmpty())
            amount = Convert.parseDecimal(value);

        return amount;
    }
}
