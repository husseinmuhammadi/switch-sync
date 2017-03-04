package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum SteganographyType implements IEnumFieldValue<String> {

    WITHOUT_STEGANOGRAPHY("0"),
    PKI_ALGORITHM_STEGANOGRAPHY("1"),
    PARTICULAR_ALGORITHM_STEGANOGRAPHY("2");


    private String stegnographyType;

    SteganographyType(String stegnographyType) {
        this.stegnographyType = stegnographyType;
    }

    public static SteganographyType getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (SteganographyType macType : values()) {
            if (macType.getValue().equals(value))
                return macType;
        }

        throw new TypeNotFoundException(SteganographyType.class.getName()
                + " Error creating instance for Stegnography Type  : " + value);
    }

    @Override
    public String getValue() {
        return this.stegnographyType;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
