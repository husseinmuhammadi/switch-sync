package com.dpi.financial.ftcom.utility.date;

import com.ibm.icu.util.Calendar;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static String getShortData(Calendar calendar) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(calendar.get(Calendar.YEAR));
        stringBuilder.append("/");
        stringBuilder.append(String.valueOf(calendar.get(Calendar.MONTH)).length() == 1 ? "0" + calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH));
        stringBuilder.append("/");
        stringBuilder.append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).length() == 1 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : calendar.get(Calendar.DAY_OF_MONTH));
        return stringBuilder.toString();
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return currentDate;
    }

    public static Date getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        return currentDate;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
