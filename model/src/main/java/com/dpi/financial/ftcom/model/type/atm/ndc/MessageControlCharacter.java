package com.dpi.financial.ftcom.model.type.atm.ndc;

import com.dpi.financial.ftcom.model.type.CharacterFieldValue;
import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.model.type.ascii.ControlCharacter;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 * http://stackoverflow.com/questions/11808869/enum-as-instance-variables
 */
public enum MessageControlCharacter implements CharacterFieldValue<ControlCharacter> {
    STX(ControlCharacter.STX),
    ETX(ControlCharacter.ETX),
    FS(ControlCharacter.FS),
    ;

    private ControlCharacter controlCharacter;

    MessageControlCharacter(ControlCharacter controlCharacter) {
        this.controlCharacter = controlCharacter;
    }

    public static MessageControlCharacter getInstance(ControlCharacter value) {
        if (value == null)
            return null;

        for (MessageControlCharacter controlCharacter : values()) {
            if (controlCharacter.getValue().equals(value))
                return controlCharacter;
        }

        throw new TypeNotFoundException(MessageControlCharacter.class.getName()
                + " Error creating instance for atm ndc MessageControlCharacter: " + value);
    }

    @Override
    public ControlCharacter getValue() {
        return this.controlCharacter;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
