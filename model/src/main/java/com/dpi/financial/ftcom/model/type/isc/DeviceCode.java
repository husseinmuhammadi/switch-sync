package com.dpi.financial.ftcom.model.type.isc;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * Device code (Terminal type code) represent device id as mentioned in SHETAB VOL 2 TRANSACTION DETAILS VERSION 7.0.4
 * System is changed from SYS to 72 as SHETAB document mentioned
 */
public enum DeviceCode implements IEnumFieldValue<String> {
    ATM("02"), // ATM
    VRU("07"), // PHONE
    MOB("05"), // MOBILE
    POS("14"), // POS
    PAD("03"), // PINPAD
    IKT("43"), // KIOSK
    INT("59"), // INTERNET
    SYS("72"); // SYSTEM

    private String deviceCode;

    DeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public static DeviceCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

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

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

}
