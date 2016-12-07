package com.dpi.financial.ftcom.security;

import com.dpi.financial.ftcom.security.api.module.SecurityModule;
import com.dpi.financial.ftcom.security.design.CardVerificationValue;
import com.dpi.financial.ftcom.security.design.ICardVerificationValue;
import com.dpi.financial.ftcom.security.design.command.Broker;

import javax.ejb.Stateless;

/**
 * Created by h.mohammadi on 11/9/2016.
 */
@Stateless
public class SecurityManager {
    private Broker broker;
    private SecurityModule module;

    private ICardVerificationValue cardVerificationValue;

    public SecurityManager() {
        broker = new Broker(module);
    }

    public ICardVerificationValue getCardVerificationValue() {
        if (cardVerificationValue == null)
            cardVerificationValue = new CardVerificationValue(null);
        return cardVerificationValue;
    }
}
