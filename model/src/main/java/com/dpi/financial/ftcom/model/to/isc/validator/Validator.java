package com.dpi.financial.ftcom.model.to.isc.validator;


import com.dpi.financial.ftcom.model.type.isc.DeviceCode;

/**
 * Created by h.mohammadi on 3/12/2017.
 */
public interface Validator {
    void validate();

    void validate(DeviceCode deviceCode);
}
