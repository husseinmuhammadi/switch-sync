package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.YesNoType;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class YesNoTypeConverter implements AttributeConverter<YesNoType, String> {
    @Override
    public String convertToDatabaseColumn(YesNoType type) {
        if (type != null)
            return type.getValue();

        return null;
    }

    @Override
    public YesNoType convertToEntityAttribute(String value) {
        return YesNoType.getInstance(value);
    }
}
