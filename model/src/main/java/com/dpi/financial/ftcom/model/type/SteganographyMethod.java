package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum SteganographyMethod implements IEnumFieldValue<String> {

    WITHOUT_STEGANOGRAPHY("0"),
    CHANAL_ENCRYPTION_STEGANOGRAPHY("1"),
    ENDTOEND_STEGANOGRAPHY("2"),
    CHANAL_ENCRYPTION_AND_ENDTOEND_STEGANOGRAPHY("3");


    private String stegnographyMethod;

    SteganographyMethod(String stegnographyMethod) {
        this.stegnographyMethod = stegnographyMethod;
    }

    public static SteganographyMethod getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (SteganographyMethod stegnographyMethod : values()) {
            if (stegnographyMethod.getValue().equals(value))
                return stegnographyMethod;
        }

        throw new TypeNotFoundException(SteganographyMethod.class.getName()
                + " Error creating instance for Stegnography Method  : " + value);
    }

    @Override
    public String getValue() {
        return this.stegnographyMethod;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
