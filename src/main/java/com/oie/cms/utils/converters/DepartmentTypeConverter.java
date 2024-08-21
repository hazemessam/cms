package com.oie.cms.utils.converters;

import com.oie.cms.enums.DepartmentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DepartmentTypeConverter implements AttributeConverter<DepartmentType, String> {
    @Override
    public String convertToDatabaseColumn(DepartmentType attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase();
    }

    @Override
    public DepartmentType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return DepartmentType.valueOf(dbData.toUpperCase());
    }
}
