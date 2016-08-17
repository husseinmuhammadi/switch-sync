package ir.team.insurance.complementary.model.type;


import ir.team.insurance.complementary.utility.exception.model.TypeNotFoundException;


public enum InsuredInsurancePlanStatusType implements EntityFieldType {
    PENDING("P"),
    ACTIVE("A"),
    CANCEL("C");

    private String statusCode;

    InsuredInsurancePlanStatusType(String s) {
        this.statusCode = s;
    }


    public static InsuredInsurancePlanStatusType getInstance(String statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (InsuredInsurancePlanStatusType insuredInsurancePlanStatusType : values()) {
            if (insuredInsurancePlanStatusType.getValue().equals(statusCode)) {
                return insuredInsurancePlanStatusType;
            }
        }

        throw new TypeNotFoundException(InsuredInsurancePlanStatusType.class.getCanonicalName() + " Status Code : " + statusCode);
    }

    @Override
    public String getValue() {
        return this.statusCode;
    }
}
