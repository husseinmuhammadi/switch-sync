package com.dpi.financial.ftcom.security.design;

import com.dpi.financial.ftcom.security.base.SecurityHandlerBase;

/**
 * Created by h.mohammadi on 11/8/2016.
 */
public class CardVerificationValue extends SecurityHandlerBase implements ICardVerificationValue {

    private ISecurityHandler securityHandler;

    public CardVerificationValue(ISecurityHandler securityHandler) {
        this.securityHandler = securityHandler;
    }
}
