package com.dpi.financial.ftcom.security.design.command.card.verification;

import com.dpi.financial.ftcom.model.to.cms.card.CardMaster;

public class CardVerificationValueService {
    private CardMaster cardMaster;

    public CardVerificationValueService(CardMaster cardMaster) {
        this.cardMaster = cardMaster;
    }

    public String generateCardVerificationValue() {
        return null;
    }
}
