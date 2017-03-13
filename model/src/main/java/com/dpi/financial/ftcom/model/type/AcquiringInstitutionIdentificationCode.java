package com.dpi.financial.ftcom.model.type;

import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

public enum AcquiringInstitutionIdentificationCode implements IEnumFieldValue<String> {
    AYN("636214"),
    ARN("504716"),
    NOV("627412"),
    ANS("627381"),
    IRZ("505785"),
    PAR("622106"),
    PAS("502229"),
    PST("627760"),
    TEJ("627353", 19),
    BTT("502908"),
    BTS("627648"),
    HEK("636949"),
    KHM("585947"),
    DAY("502938"),
    RST("504172"),
    REF("589463"),
    SAM("621986"),
    SEP("589210", 19),
    SAR("639607"),
    SIN("639346"),
    SHR("504706"),
    BSI("603769"),
    BSM("627961"),
    MHR("606373"),
    KAR("627488"),
    BKI("603770"),
    GAR("505416"),
    CNT("636795"),
    MSK("628023"),
    MEL("610433"),
    MBI("603799"),
    CID("628157"),
    KSA("505801"),
    ASK("606256"),
    ;

    private final String acquiringInstitutionIdentificationCode;
    private final int panLength;

    AcquiringInstitutionIdentificationCode(String processingCode) {
        this(processingCode, 16);
    }

    AcquiringInstitutionIdentificationCode(String acquiringInstitutionIdentificationCode, int panLength) {
        this.acquiringInstitutionIdentificationCode = acquiringInstitutionIdentificationCode;
        this.panLength = panLength;
    }

    public static AcquiringInstitutionIdentificationCode getInstance(String value) {
        if (value == null || value.isEmpty())
            return null;

        for (AcquiringInstitutionIdentificationCode acquiringInstitutionIdentificationCode : values()) {
            if (acquiringInstitutionIdentificationCode.getValue().equals(value))
                return acquiringInstitutionIdentificationCode;
        }

        throw new TypeNotFoundException(AcquiringInstitutionIdentificationCode.class.getName()
                + " Error creating instance for Acquiring Institution Identification Code : " + value);
    }

    @Override
    public String getValue() {
        return this.acquiringInstitutionIdentificationCode;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

    public int getPanLength() {
        return panLength;
    }
}
