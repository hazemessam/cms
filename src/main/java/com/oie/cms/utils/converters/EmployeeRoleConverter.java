package com.oie.cms.utils.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.oie.cms.enums.EmployeeRole;

@Converter(autoApply = true)
public class EmployeeRoleConverter implements AttributeConverter<EmployeeRole, String> {

    @Override
    public String convertToDatabaseColumn(EmployeeRole attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public EmployeeRole convertToEntityAttribute(String dbData) {
        return dbData == null ? null : EmployeeRole.valueOf(dbData.toUpperCase());
    }
}
