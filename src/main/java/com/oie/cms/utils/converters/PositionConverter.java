package com.oie.cms.utils.converters;

import com.oie.cms.enums.Position;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PositionConverter implements AttributeConverter<Position, String> {
    @Override
    public String convertToDatabaseColumn(Position attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public Position convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Position.valueOf(dbData.toUpperCase());
    }
}
