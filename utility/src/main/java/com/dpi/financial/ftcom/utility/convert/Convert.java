package com.dpi.financial.ftcom.utility.convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Created by h.mohammadi on 12/11/2016.
 */
public class Convert {
    public static BigDecimal parseDecimal(String value) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        // parse the string
        BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(value);
        return bigDecimal;
    }
}
