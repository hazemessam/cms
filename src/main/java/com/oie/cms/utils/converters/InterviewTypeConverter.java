package com.oie.cms.utils.converters;

import com.oie.cms.enums.InterviewType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InterviewTypeConverter implements AttributeConverter<InterviewType, String> {
    @Override
    public String convertToDatabaseColumn(InterviewType attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public InterviewType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : InterviewType.valueOf(dbData.toUpperCase());
    }
}
