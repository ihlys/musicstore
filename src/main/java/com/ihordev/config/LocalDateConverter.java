package com.ihordev.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;


@SuppressWarnings("nullness")
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate entityDate) {
        return entityDate == null ? null : Date.valueOf(entityDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbDate) {
        return dbDate == null ? null : dbDate.toLocalDate();
    }
}
