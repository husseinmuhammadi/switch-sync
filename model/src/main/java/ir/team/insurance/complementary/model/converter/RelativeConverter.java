package ir.team.insurance.complementary.model.converter;

import ir.team.insurance.complementary.model.type.RelativeType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RelativeConverter implements AttributeConverter<RelativeType, String> {
    @Override
    public String convertToDatabaseColumn(RelativeType relativeType) {
        return relativeType.getValue();
    }

    @Override
    public RelativeType convertToEntityAttribute(String relativeCode) {
        return RelativeType.getInstance(relativeCode);
    }
}
