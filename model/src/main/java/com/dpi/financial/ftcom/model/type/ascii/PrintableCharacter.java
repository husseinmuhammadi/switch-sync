package com.dpi.financial.ftcom.model.type.ascii;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 *
 */
public enum PrintableCharacter implements IEnumFieldValue<Integer> {
    SPACE(0x20, " ", "&#32;", null, "Space"),
    ;

    private final int value;
    private final String symbol;
    private final String htmlNumber;
    private final String htmlName;
    private final String description;

    PrintableCharacter(int value, String symbol, String htmlNumber, String htmlName, String description) {
        this.value = value;
        this.symbol = symbol;
        this.htmlNumber = htmlNumber;
        this.htmlName = htmlName;
        this.description = description;
    }

    public static PrintableCharacter getInstance(Integer value) {
        if (value == null)
            return null;

        for (PrintableCharacter controlCharacter : values()) {
            if (controlCharacter.getValue().equals(value))
                return controlCharacter;
        }

        throw new TypeNotFoundException(PrintableCharacter.class.getName()
                + " Error creating instance for ascii ControlCharacter: " + value);
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getHtmlNumber() {
        return htmlNumber;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
