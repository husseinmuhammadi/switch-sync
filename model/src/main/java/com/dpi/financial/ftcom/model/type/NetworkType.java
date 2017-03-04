package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum NetworkType implements IEnumFieldValue<String> {

    PRIVATE_NETWORK("1"),
    OPEN_NETWORK("2");


    private String networktype;

    NetworkType(String networktype) {
        this.networktype = networktype;
    }

    public static NetworkType getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (NetworkType networktype : values()) {
            if (networktype.getValue().equals(value))
                return networktype;
        }

        throw new TypeNotFoundException(NetworkType.class.getName()
                + " Error creating instance for Network Type  : " + value);
    }

    @Override
    public String getValue() {
        return this.networktype;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
