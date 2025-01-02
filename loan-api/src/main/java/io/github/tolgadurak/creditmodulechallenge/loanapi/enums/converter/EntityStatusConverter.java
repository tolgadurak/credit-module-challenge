package io.github.tolgadurak.creditmodulechallenge.loanapi.enums.converter;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.EntityStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EntityStatusConverter implements AttributeConverter<EntityStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(EntityStatus attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public EntityStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? EntityStatus.of(dbData) : null;
    }
}
