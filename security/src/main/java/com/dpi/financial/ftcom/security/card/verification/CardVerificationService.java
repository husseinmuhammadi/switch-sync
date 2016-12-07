package com.dpi.financial.ftcom.security.card.verification;

import com.dpi.financial.ftcom.security.api.CardVerification;
import com.dpi.financial.ftcom.security.base.SecurityHandlerBase;

import java.util.Date;

public class CardVerificationService extends SecurityHandlerBase implements CardVerification {
    @Override
    public String generateCvv1(String pan, Date expDate) {
        return null;
    }

    @Override
    public String generateCvv2(String pan, Date expDate) {
        return null;
    }

    @Override
    public String generateCvv3(String pan, Date expDate) {
        return null;
    }
}
