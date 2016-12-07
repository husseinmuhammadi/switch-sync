package com.dpi.financial.ftcom.service.base.cms.card;

import com.dpi.financial.ftcom.api.base.cms.card.MagneticStripeService;
import com.dpi.financial.ftcom.core.codec.Parser;
import com.dpi.financial.ftcom.model.base.GenericDao;
import com.dpi.financial.ftcom.model.dao.cms.card.MagneticStripeDao;
import com.dpi.financial.ftcom.model.to.cms.card.MagneticStripe;
import com.dpi.financial.ftcom.security.*;
import com.dpi.financial.ftcom.security.SecurityManager;
import com.dpi.financial.ftcom.security.base.SecurityBase;
import com.dpi.financial.ftcom.service.GeneralServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
@Local(MagneticStripeService.class)
public class MagneticStripeServiceImpl extends GeneralServiceImpl<MagneticStripe>
        implements MagneticStripeService {

    @EJB
    private MagneticStripeDao dao;

    @EJB
    private SecurityManager securityManager;

    @Override
    public GenericDao<MagneticStripe> getGenericDao() {
        return dao;
    }

    @Override
    public MagneticStripe findByPan(MagneticStripe magneticStripe) {
        return dao.findByPan(magneticStripe);
    }

    @Override
    public MagneticStripe generateTrackData(String primaryAccountNumber) {

        MagneticStripe magneticStripe = new MagneticStripe();
        magneticStripe.setPan(primaryAccountNumber);

        magneticStripe = dao.findByPan(magneticStripe);

        String track2Data = null;

        System.out.println("Generate track 2 data ... ");
        System.out.println("Card Number : " + primaryAccountNumber);

        String pan = primaryAccountNumber.substring(3, 15);
        System.out.println("Pan is : " + pan);

        Date expDate = magneticStripe.getCardMaster().getExpirationDate();
        System.out.println("Expire Date : " + expDate);

        String serviceCode = "101";

        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String expdt = sdf.format(expDate);

        try {
            // securityManager.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        String cvv1 = generateCvv1(cardNumber, expDate);

        if (cvv1 != null)
            track2Data = generateTrack2Data(cardNumber, expdt, serviceCode, cvv1);
        */

        return magneticStripe;
    }
}
