package ir.team.insurance.complementary.model.type;


import ir.team.insurance.complementary.utility.exception.model.TypeNotFoundException;

public enum StatusType implements EntityFieldType {
    ENTRY("E"),
    PENDING("P"),
    ACTIVE("A"),
    INACTIVE("I"),
    DELETED("D");

    private String statusCode;

    StatusType(String s) {
        this.statusCode = s;
    }



    public static StatusType getInstance(String statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (StatusType statusType : values()) {
            if (statusType.getValue().equals(statusCode)) {
                return statusType;
            }
        }
        throw new TypeNotFoundException(StatusType.class.getName() + " Status code :" +
                statusCode);
    }

    @Override
    public String getValue() {
        return this.statusCode;
    }

    @Deprecated
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }

    @Deprecated
    public String getFullName(String tableName) {
        return getFullName() + "." + tableName;
    }
}
