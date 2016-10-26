package com.dpi.financial.ftcom.model.converter;


import com.dpi.financial.ftcom.model.type.LocaleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocaleConverter implements AttributeConverter<LocaleType, String> {
    @Override
    public String convertToDatabaseColumn(LocaleType attribute) {
        return attribute.getValue();
    }

    @Override
    public LocaleType convertToEntityAttribute(String dbData) {
        return LocaleType.getInstance(dbData);
    }
}
