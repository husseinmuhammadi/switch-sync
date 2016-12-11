package com.dpi.financial.ftcom.model.type.atm.ndc;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum Language implements IEnumFieldValue<String> {
    PERSIAN("00"),
    ENGLISH("01"),
    ;

    private String language;

    Language(String language) {
        this.language = language;
    }

    public static Language getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (Language language : values()) {
            if (language.getValue().equals(value))
                return language;
        }

        throw new TypeNotFoundException(Language.class.getName()
                + " Error creating instance for ATM NDC Language: " + value);
    }

    @Override
    public String getValue() {
        return this.language;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
