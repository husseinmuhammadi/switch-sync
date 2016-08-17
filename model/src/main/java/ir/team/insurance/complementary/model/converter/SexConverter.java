package ir.team.insurance.complementary.model.converter;

import ir.team.insurance.complementary.model.type.SexType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SexConverter implements AttributeConverter<SexType, String> {
    @Override
    public String convertToDatabaseColumn(SexType sexType) {
        return sexType.getValue();
    }

    @Override
    public SexType convertToEntityAttribute(String sexCode) {
        return SexType.getInstance(sexCode);
    }
}
