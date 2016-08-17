package ir.team.insurance.complementary.utility.data;

import com.ibm.icu.util.Calendar;

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
}
