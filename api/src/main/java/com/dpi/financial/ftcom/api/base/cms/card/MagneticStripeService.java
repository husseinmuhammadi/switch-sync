package com.dpi.financial.ftcom.api.base.cms.card;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;

public interface MagneticStripeService extends GeneralServiceApi<MagneticStripe> {
    MagneticStripe findByPan(MagneticStripe magneticStripe);

    MagneticStripe generateTrackData(String pan);
}
