package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum MacType implements IEnumFieldValue<String> {

    WITHOUT_MAC("0"),
    PARTICULAR_ALGORITHM_MAC("1"),
    STANDARD_ALGORITHM_MAC("2");


    private String macType;

    MacType(String macType) {
        this.macType = macType;
    }

    public static MacType getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (MacType macType : values()) {
            if (macType.getValue().equals(value))
                return macType;
        }

        throw new TypeNotFoundException(MacType.class.getName()
                + " Error creating instance for Mac Type  : " + value);
    }

    @Override
    public String getValue() {
        return this.macType;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
