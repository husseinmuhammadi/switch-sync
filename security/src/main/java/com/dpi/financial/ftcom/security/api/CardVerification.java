package com.dpi.financial.ftcom.security.api;

import java.util.Date;

/**
 * Created by h.mohammadi on 11/8/2016.
 */
public interface CardVerification {
    String generateCvv1(String pan, Date expDate);

    String generateCvv2(String pan, Date expDate);

    String generateCvv3(String pan, Date expDate);
}
