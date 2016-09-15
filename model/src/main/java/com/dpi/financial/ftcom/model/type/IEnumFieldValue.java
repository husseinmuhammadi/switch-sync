package com.dpi.financial.ftcom.model.type;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public interface IEnumFieldValue<T> extends EnumField {
    T getValue();
    // static IFieldValue getInstance(T value);
}