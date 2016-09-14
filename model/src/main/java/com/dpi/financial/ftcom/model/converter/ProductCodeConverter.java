package com.dpi.financial.ftcom.model.converter;

import com.dpi.financial.ftcom.model.type.ProductCode;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class ProductCodeConverter implements AttributeConverter<ProductCode, String> {
    @Override
    public String convertToDatabaseColumn(ProductCode productCode) {
        return productCode.getValue();
    }

    @Override
    public ProductCode convertToEntityAttribute(String value) {
        return ProductCode.getInstance(value);
    }
}
