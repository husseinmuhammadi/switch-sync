package ir.team.insurance.complementary.model.type;


import ir.team.insurance.complementary.utility.exception.model.TypeNotFoundException;

public enum RelativeType implements EntityFieldType {
    MainInsured("I"),
    Father("F"),
    Mother("M"),
    Brother("B"),
    Sister("S"),
    Son("O"),
    Daughter("D"),
    Spouse("P"),
    //Wife("W"),
    //GrandSon("GS"),
    //GrandDaughter("GD"),
    GrandChild("GC"),
    BrotherInLow("BL"),
    SisterInLow("SL");

    private String relativeCode;

    @Override
    public String getValue() {
        return relativeCode;
    }

    RelativeType(String relativeCode) {
        this.relativeCode = relativeCode;
    }

    public static RelativeType getInstance(String relativeCode) {
        if (relativeCode == null) {
            return null;
        }
        for (RelativeType relativeType : values()) {
            if (relativeType.getValue().equals(relativeCode)) {
                return relativeType;
            }
        }
        throw new TypeNotFoundException(RelativeType.class.getName() + " Relative code :" +
                relativeCode);
    }
}
