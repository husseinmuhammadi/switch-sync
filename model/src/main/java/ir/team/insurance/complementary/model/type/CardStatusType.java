package ir.team.insurance.complementary.model.type;

import com.dpi.financial.ftcom.model.type.EntityFieldType;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

public enum CardStatusType implements EntityFieldType {
    PENDING("P"),
    ACTIVE("A"),
    CANCEL("C");

    private String statusCode;

    CardStatusType(String s) {
        this.statusCode = s;
    }


    public static CardStatusType getInstance(String statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (CardStatusType cardStatusType : values()) {
            if (cardStatusType.getValue().equals(statusCode)) {
                return cardStatusType;
            }
        }

        throw new TypeNotFoundException(StatusType.class.getCanonicalName() + " Status Code : " + statusCode);
    }

    @Override
    public String getValue() {
        return this.statusCode;
    }
}
