package com.dpi.financial.ftcom.core.atm.iso.ndc.msg;

import com.dpi.financial.ftcom.model.type.atm.ndc.MessageIdentifier;

/**
 * Unsolicited Messages: we do not send response to ATM on unsolicited message
 */
public class UnsolicitedMessage extends TerminalToNetworkMessages {
    protected final MessageIdentifier identifier = MessageIdentifier.UNSOLICITED_STATUS;

    private String luno;
    private String statusInformation;

    public UnsolicitedMessage() {
        super();
    }

    @Override
    public MessageIdentifier getIdentifier() {
        return identifier;
    }

    public String getLuno() {
        return luno;
    }

    public void setLuno(String luno) {
        this.luno = luno;
    }

    public String getStatusInformation() {
        return statusInformation;
    }

    public void setStatusInformation(String statusInformation) {
        this.statusInformation = statusInformation;
    }
}
