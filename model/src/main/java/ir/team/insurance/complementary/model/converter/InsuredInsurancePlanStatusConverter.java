package ir.team.insurance.complementary.model.converter;

import ir.team.insurance.complementary.model.type.InsuredInsurancePlanStatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class InsuredInsurancePlanStatusConverter implements AttributeConverter<InsuredInsurancePlanStatusType, String> {
    @Override
    public String convertToDatabaseColumn(InsuredInsurancePlanStatusType insuredInsurancePlanStatusType) {
        return insuredInsurancePlanStatusType.getValue();
    }

    @Override
    public InsuredInsurancePlanStatusType convertToEntityAttribute(String statusCode) {
        return InsuredInsurancePlanStatusType.getInstance(statusCode);
    }
}
