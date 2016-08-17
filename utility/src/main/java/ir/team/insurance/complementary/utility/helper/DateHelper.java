package ir.team.insurance.complementary.utility.helper;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static int getAge(Date birthDate, Date toDate) {

        if (null == birthDate || null == toDate) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        LocalDate insuredBirthday = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        calendar.setTime(toDate);
        LocalDate toDateLocalDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        return Period.between(insuredBirthday, toDateLocalDate).getYears();
    }

    public static int getAge(final Date birthDate) {
        return getAge(birthDate, new Date());
    }
}
