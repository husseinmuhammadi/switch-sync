package com.dpi.financial.ftcom.web.controller.base.simulator.std.field.base;


import com.dpi.financial.ftcom.web.controller.base.simulator.std.definition.ISOField;
import  com.dpi.financial.ftcom.web.controller.base.simulator.std.message.base.ISOConstant;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;


/**
 * @since ver 2.1.6-P7 Added by Mostafa w.r.t feature #12567 as on on 9 June 2016
 * <li>Update original data elements (Field Value 90) for reversal request send to SHETAB</li>
 */


public class ISOFieldBase {

    private static Logger logger = Logger.getLogger(ISOFieldBase.class);
    private static Logger errorLogger = Logger.getLogger("errordetailslog");

    public static final int PROCESSING_CODE = ISOField.PROCESSING_CODE.getValue();

    // Data Field 90 - Original data elements
    public static String getOriginalDataElements(String mti, String stan,
                                                 String localTransactionDate, String localTransactionTime,
                                                 String acquiringInstitutionIdentificationCode, String forwardingInstitutionIdentificationCode) {
        String originalDataElements = "";

        try {
            originalDataElements = mti + stan + localTransactionDate + localTransactionTime +
                    ISOUtil.zeropad(acquiringInstitutionIdentificationCode, 11) +
                    ISOUtil.zeropad(forwardingInstitutionIdentificationCode, 11);
        } catch (ISOException e) {
            e.printStackTrace();
        }

        return originalDataElements;
    }

    // This Method generate Field 90
    public static String getOriginalDataElements(ISOMsg isoMsg) {
        String originalDataElements = "";

        try {
            originalDataElements = isoMsg.getMTI()
                    + isoMsg.getString(ISOConstant.ISO_FIELD_STAN)
                    + isoMsg.getString(ISOConstant.ISO_FIELD_CAPTUREDATE)
                    + isoMsg.getString(ISOConstant.ISO_FIELD_TXNDATE)
                    + ISOUtil.zeropad(isoMsg.getString(ISOConstant.ISO_FIELD_ACQURING_ISNT_CODE), 11)
                    + ISOUtil.zeropad(isoMsg.getString(ISOConstant.ISO_FIELD_FORWARDING_ISNT_CODE), 11);

            if (isoMsg.getMTI().equals("420") || isoMsg.getMTI().equals("400"))
                logger.info("Error in original data element - MTI is : " + isoMsg.getMTI());

        } catch (ISOException e) {
            logger.error(e.getMessage());
            errorLogger.error(e.getMessage());
        }

        return originalDataElements;
    }
}
