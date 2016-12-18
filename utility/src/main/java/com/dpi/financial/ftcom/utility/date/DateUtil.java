package com.dpi.financial.ftcom.utility.date;

import com.ibm.icu.util.Calendar;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class DateUtil {
    public static String getShortDate(Calendar calendar) {
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

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static boolean isBetweenDate(Date date, Date from, Date to) {
        {
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
            sdf = new SimpleDateFormat("MM/dd/yyyy");

            if (date.equals(removeTime(from)))
                return true;

            if (date.equals(removeTime(to)))
                return true;

            if (date.before(removeTime(to)) && date.after(removeTime(from)))
                return true;

            return false;
        }
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDate(int year, int month, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        return asDate(localDate);
    }

    public static Date getDate(int year, Month month, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        return asDate(localDate);
    }
}
