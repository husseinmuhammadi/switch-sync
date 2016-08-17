package ir.team.insurance.complementary.model.type;


import ir.team.insurance.complementary.utility.exception.model.TypeNotFoundException;

public enum SexType implements EntityFieldType {
    Man("M"),
    Woman("W");

    private String sexCode;

    SexType(String s) {
        this.sexCode = s;
    }



    public static SexType getInstance(String sexCode) {
        if (sexCode == null) {
            return null;
        }
        for (SexType sexType : values()) {
            if (sexType.getValue().equals(sexCode)) {
                return sexType;
            }
        }
        throw new TypeNotFoundException(SexType.class.getName() + " Sex code :" +
                sexCode);
    }

    @Override
    public String getValue() {
        return this.sexCode;
    }
}
