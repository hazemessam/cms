package com.oie.cms.utils.converters;

import com.oie.cms.enums.ReferralType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReferralTypeConverter implements AttributeConverter<ReferralType, String> {
    @Override
    public String convertToDatabaseColumn(ReferralType attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public ReferralType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ReferralType.valueOf(dbData.toUpperCase());
    }
}
