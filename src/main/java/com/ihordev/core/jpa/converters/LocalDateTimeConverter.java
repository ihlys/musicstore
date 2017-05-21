package com.ihordev.core.jpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime entityDateTime) {
        return entityDateTime == null ? null : Timestamp.valueOf(entityDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbTimestamp) {
        return dbTimestamp == null ? null : dbTimestamp.toLocalDateTime();
    }
}
