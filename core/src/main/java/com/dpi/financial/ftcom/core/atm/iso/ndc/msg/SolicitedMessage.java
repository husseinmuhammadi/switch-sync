package com.dpi.financial.ftcom.core.atm.iso.ndc.msg;

import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;

/**
 * After the terminal completes the transaction with the customer, it sends a solicited status message to the network.
 */
public class SolicitedMessage extends TerminalToNetworkMessages {
    protected final MessageIdentifier identifier = MessageIdentifier.SOLICITED_STATUS;

    @Override
    public MessageIdentifier getIdentifier() {
        return identifier;
    }

    private String primaryAccountNumber;
    private String pan;

    private String personalIdentificationNumber;
    private String pin;
}
