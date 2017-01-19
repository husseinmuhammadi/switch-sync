package com.dpi.financial.ftcom.core.atm.iso.ndc.msg;

import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;

/**
 * Created by h.mohammadi on 12/29/2016.
 */
public class TransactionRequest extends TerminalToNetworkMessages {
    protected final MessageIdentifier identifier = MessageIdentifier.TRANSACTION_REQUEST;


    @Override
    public MessageIdentifier getIdentifier() {
        return identifier;
    }

    // private String primaryAccountNumber;
    private String pan;

    // private String personalIdentificationNumber;
    private String pin;


}
