package com.dpi.financial.ftcom.model.type.isc.transaction;

/**
 * Created by h.mohammadi on 3/11/2017.
 */
public class PrimaryAccountNumber implements Securable {

    private final String pan;

    public PrimaryAccountNumber(String pan) {
        this.pan = pan;
    }

    @Override
    public String toString() {
        return pan;
    }

    @Override
    public String encrypt() {
        return null;
    }

    @Override
    public String decrypt() {
        return null;
    }
}
