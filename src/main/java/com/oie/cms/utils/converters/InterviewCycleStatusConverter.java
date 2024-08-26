package com.oie.cms.utils.converters;

import com.oie.cms.enums.InterviewCycleStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InterviewCycleStatusConverter implements AttributeConverter<InterviewCycleStatus, String> {
    @Override
    public String convertToDatabaseColumn(InterviewCycleStatus attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public InterviewCycleStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : InterviewCycleStatus.valueOf(dbData.toUpperCase());
    }
}
