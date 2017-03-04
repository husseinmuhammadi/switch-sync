package com.dpi.financial.ftcom.web.controller.base.simulator.field;

import com.dpi.financial.ftcom.model.type.FunctionCode;
import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.MessageDetails;
import org.jpos.iso.ISOUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mo.soltani on 2/25/2017.
 */
public class CreateSpecialField {


    public static long stan;


    //get field 11
    public static String getStan(Date date) {
        String field11;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mmssSSS");
        String strDate = simpleDateFormat.format(date);
        stan = 200007;
        stan = Integer.parseInt(strDate.substring(0, 6));
        System.out.println("stan : " + stan);
        stan = stan + 1000;
        field11 = (new Long(stan)).toString();
        return field11;

    }

    //get field 22
    public static String gePointServiceEntrymode(MessageDetails messageDetails) {
        String field22 = "";
        String processingCode = messageDetails.getProcessingCode();
        String deviceCode = messageDetails.getDeviceCode();
        // String productcode=messageDetails.getProductCode();


        switch (processingCode) {

            case "01":
            case "13":
            case "40":
            case "46":
            case "60":
                field22 = "27";

            case "27":
            case "28":
            case "33":
            case "47":
                field22 = "12";

            case "31":
            case "34":
                field22 = "21";

            case "20":
                if (deviceCode.equals("14") || deviceCode.equals("43"))
                    field22 = "21";
                else
                    field22 = "11";

            case "50":
                if (deviceCode.equals("02") || deviceCode.equals("14") || deviceCode.equals("03") || deviceCode.equals("43"))
                    field22 = "27";
                else
                    field22 = "17";

            case "71":
                if (deviceCode.equals("02") || deviceCode.equals("14") || deviceCode.equals("03") || deviceCode.equals("43"))
                    field22 = "21";
                else
                    field22 = "11";
            case "90":
                field22 = "000";


        }

        return field22;
    }


    //get field 25
    public static String getPointserviceConditionCode(MessageDetails messageDetails) {
        String field25 = "";
        String deviceCode = messageDetails.getDeviceCode();


        switch (deviceCode) {
            case "02":
            case "43":
                field25 = "02";
                break;
            case "14":
            case "03":
                field25 = "10";
                break;
            case "07":
            case "05":
            case "59":
                field25 = "08";
                break;
            case "72":
                field25 = "00";
                break;

        }

        return field25;
    }

    //get field 26
    public static String getPointserviceCaptureCode(MessageDetails messageDetails) {

        String field26 = "";
        String deviceCode = messageDetails.getDeviceCode();

        switch (deviceCode) {
            case "02":
            case "14":
            case "03":
            case "43":
                field26 = "04";
                break;
            case "07":
            case "05":
            case "59":
            case "72":
                field26 = "12";
                break;
        }

        return field26;

    }


    //get field 35
    public static String getTrack2(MessageDetails messageDetails) {

        String track2 = "";
        try {
            long keyIndex = ((CreateSpecialField.stan % 2) + 1) + 1;
            if (keyIndex == 0)
                keyIndex = 2;
            String keyIndexStr = keyIndex + "";
            while (keyIndexStr.length() < 2)
                keyIndexStr = "0" + keyIndexStr;

            String[] csdKey = {"1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C", ""};
            track2 = Mac.instance.encTrack2(ISOUtil.hex2byte(csdKey[0]), messageDetails.getTrack2(), csdKey[1].equals("1"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return track2;
    }


    //get field 37
    public static String getRrn(Date date) {
        String field37 = "";
        SimpleDateFormat simpleDateFormatFirst = new SimpleDateFormat("MMddHHmmss");
        String strDateFirst = simpleDateFormatFirst.format(date);
        SimpleDateFormat simpleDateFormatSecond = new SimpleDateFormat("mmssSSS");
        String strDateSecond = simpleDateFormatSecond.format(date);
        String rrn = "531111";
        rrn = "53" + strDateFirst.substring(0, 4) + strDateSecond.substring(0, 6);
        field37 = rrn;
        return field37;
    }

    //get Field 48
    public static String getAdditionalData(MessageDetails messageDetails) {


        String field48 = "";
        String reserve = "      ";// 6 char
        String lang = "00";// 01:english 00:farsi
        field48 = reserve + lang;
        field48 += messageDetails.getPan();
        return field48;

    }

    //get field 52
    public static byte[] personalIdenNumPartOne(MessageDetails messageDetails) {

        byte[] bit52 = new byte[8];

        byte[] pinBlock = getPinBlock(messageDetails);

        try {
            System.arraycopy(pinBlock, 0, bit52, 0, 8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bit52;
    }


    //get field 60 Part Two
    public static byte[] personalIdenNumPartTwo(MessageDetails messageDetails) {

        byte[] pinBit60 = new byte[8];

        byte[] pinBlock = getPinBlock(messageDetails);

        try {

            if (pinBlock.length > 8)
                System.arraycopy(pinBlock, 8, pinBit60, 0, 8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pinBit60;
    }


    //get field 62
    public static String getTransactonCoding(MessageDetails messageDetails) {
        String field62 = "";
        String messErrorIndicator = "000";
        String messReasonCode = "0000";
        String shetabControllingData = "00000000";
        String processingCode=messageDetails.getProcessingCode();
        String functionCode="000";
        switch (processingCode) {
            case "40":
                functionCode = FunctionCode.FUND_TRANSFER.getValue();
                break;
            case "46":
                functionCode = FunctionCode.FUND_TRANSFER_DR.getValue();
                break;
            case "47":
                functionCode = FunctionCode.FUND_TRANSFER_CR.getValue();
                break;
        }




      //  field62 = messageDetails.getDeviceCode()+ messErrorIndicator + messReasonCode + functionCode + secutiryConfig + shetabControllingData + tranSupplData + cardAccSupplData + panEnc + sourceCardEnc;
        return field62;

    }


    //get PinBlock
    public static byte[] getPinBlock(MessageDetails messageDetails) {

        byte[] pinBlock = null;
        try {
            long keyIndex = ((CreateSpecialField.stan % 2) + 1) + 1;
            if (keyIndex == 0)
                keyIndex = 2;
            String keyIndexStr = keyIndex + "";
            while (keyIndexStr.length() < 2)
                keyIndexStr = "0" + keyIndexStr;

            String[] pinKey = CreateSpecialField.getKey("02", keyIndex);
            pinBlock = Mac.instance.pinEnc(messageDetails.getPin(), messageDetails.getPan(), ISOUtil.hex2byte(pinKey[0]), pinKey[1].equals("1"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pinBlock;
    }


    //mostafa
    public static String[] getKey(String type, long index) {
        String key = "";
        String aes = "";
        if (type.equals("02")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
        }
        if (type.equals("03")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
        }
        if (type.equals("04")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
            aes = "1";
        }
        if (type.equals("05")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
            aes = "1";
        }
        if (type.equals("06")) {
            key = "1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
            aes = "1";
        }
        return new String[]{key, aes};
    }


}
