package com.en.std.definition;

/**
 * Created by h.mohammadi on 6/19/2016.
 */
public enum ISOField implements IEnumFieldValue<Integer> {
    PROCESSING_CODE(3);

    private final int isoField;

    ISOField(int isoField) {
        this.isoField = isoField;
    }

    @Override
    public Integer getValue() {
        return isoField;
    }
}
