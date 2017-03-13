package com.dpi.financial.ftcom.model.converter;


import com.dpi.financial.ftcom.model.type.isc.DeviceCode;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class DeviceCodeConverter implements AttributeConverter<DeviceCode, String> {
    @Override
    public String convertToDatabaseColumn(DeviceCode deviceCode) {
        if (deviceCode != null)
            return deviceCode.getValue();

        return null;
    }

    @Override
    public DeviceCode convertToEntityAttribute(String value) {
        return DeviceCode.getInstance(value);
    }
}
