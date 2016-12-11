package com.dpi.financial.ftcom.model.converter.atm.ndc;

import com.dpi.financial.ftcom.model.type.atm.ndc.Language;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class LanguageConverter implements AttributeConverter<Language, String> {
    @Override
    public String convertToDatabaseColumn(Language language) {
        if (language != null)
            return language.getValue();

        return null;
    }

    @Override
    public Language convertToEntityAttribute(String value) {
        return Language.getInstance(value);
    }
}
