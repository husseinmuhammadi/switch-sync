package ir.team.insurance.complementary.model.converter;

import ir.team.insurance.complementary.model.type.StatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<StatusType, String> {
    @Override
    public String convertToDatabaseColumn(StatusType statusType) {
        return statusType.getValue();
    }

    @Override
    public StatusType convertToEntityAttribute(String statusCode) {
        return StatusType.getInstance(statusCode);
    }
}
