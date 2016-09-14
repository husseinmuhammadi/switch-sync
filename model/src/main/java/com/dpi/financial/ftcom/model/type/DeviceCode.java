package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public enum DeviceCode implements IEnumFieldValue<String> {
    ATM("02"), // ATM
    POS("14"), // POS
    PAD("03"), // PINPAD
    INT("59"), // INTERNET
    VRU("07"), // PHONE
    MOB("05"), // MOBILE
    IKT("43"), // KIOSK
    SYS("SYS"); // SYSTEM

    private String deviceCode;

    DeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public static DeviceCode getInstance(String value) {
        for (DeviceCode deviceCode : values()) {
            if (deviceCode.getValue().equals(value))
                return deviceCode;
        }
        throw new TypeNotFoundException(DeviceCode.class.getName()
                + " Error creating instance for device code: " + value);
    }

    @Override
    public String getValue() {
        return this.deviceCode;
    }
}
