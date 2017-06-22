package com.ihordev.config;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@SuppressWarnings("nullness")
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public @Nullable Timestamp convertToDatabaseColumn(@Nullable LocalDateTime entityDateTime) {
        return entityDateTime == null ? null : Timestamp.valueOf(entityDateTime);
    }

    @Override
    public @Nullable LocalDateTime convertToEntityAttribute(@Nullable Timestamp dbTimestamp) {
        return dbTimestamp == null ? null : dbTimestamp.toLocalDateTime();
    }
}
