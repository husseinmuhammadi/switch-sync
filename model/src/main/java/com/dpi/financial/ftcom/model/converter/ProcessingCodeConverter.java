package com.dpi.financial.ftcom.model.converter;


import com.dpi.financial.ftcom.model.type.ProcessingCode;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class ProcessingCodeConverter implements AttributeConverter<ProcessingCode, String> {
    @Override
    public String convertToDatabaseColumn(ProcessingCode processingCode) {
        return processingCode.getValue();
    }

    @Override
    public ProcessingCode convertToEntityAttribute(String processingCodeValue) {
        return ProcessingCode.getInstance(processingCodeValue);
    }
}
