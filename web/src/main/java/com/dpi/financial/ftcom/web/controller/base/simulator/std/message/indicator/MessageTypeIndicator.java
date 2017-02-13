package com.dpi.financial.ftcom.web.controller.base.simulator.std.message.indicator;

import com.dpi.financial.ftcom.web.controller.base.simulator.std.exception.InvalidArgumentException;


/**
 * A message type indicator includes
 * the ISO 8583 version, the Message Class, the Message Function and the Message Origin,
 * each described briefly in the following sections.
 * The following example (MTI 0110) lists what each digit indicates:
 * 0xxx -> version of ISO 8583 (for example: 1987 version)
 * x1xx -> class of the Message for example: Authorization Message)
 * xx1x -> function of the Message (for example: Request Response)
 * xxx0 -> who began the communication (for example: Acquirer)
 */
public class MessageTypeIndicator {

    public MessageTypeIndicator(String mti) {
        this.mti = mti;
        validate();
        version = Character.getNumericValue(mti.charAt(0));
        classification = Character.getNumericValue(mti.charAt(1));
        function = Character.getNumericValue(mti.charAt(2));
        origin = Character.getNumericValue(mti.charAt(3));
    }

    private void validate() {
        if (mti == null || mti.length() != 4)
            throw new InvalidArgumentException(MessageTypeIndicator.class.getName()
                    + " Message Type Indicator Code :" + mti);
    }

    public MessageTypeIndicator(int version, int classification, int function, int origin) {
        this.classification = classification;
        this.function = function;
        this.origin = origin;
        this.version = version;
    }

    protected String mti;
    protected int version;
    protected int classification;
    protected int function;
    protected int origin;


    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
        validate();
        version = Character.getNumericValue(mti.charAt(0));
        classification = Character.getNumericValue(mti.charAt(1));
        function = Character.getNumericValue(mti.charAt(2));
        origin = Character.getNumericValue(mti.charAt(3));
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return mti;
    }
}
