package com.dpi.financial.ftcom.security.design;

import com.dpi.financial.ftcom.security.base.SecurityHandlerBase;

/**
 * This class is responsible for combining security set of commands
 * and the SECURITY HANDLER BASE which is responsible for
 * managing module and ciphers and keys
 * and low level commands
 *
 * This class using peer pattern
 */
public class SecurityHandler extends SecurityHandlerBase
        implements ISecurityHandler {
    ICardVerificationValue cardVerificationValue;

    public SecurityHandler() {
        cardVerificationValue = new CardVerificationValue(this);
    }

    public ICardVerificationValue getCardVerificationValue() {
        return cardVerificationValue;
    }
}
