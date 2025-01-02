package io.github.tolgadurak.creditmodulechallenge.loanapi.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.AbstractEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.EntityStatus;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public class EntityBaseMapper {
    @BeforeMapping
    public void beforeAbstractEntityMapping(@MappingTarget AbstractEntity entity) {
        entity.setEntityStatus(EntityStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setReferenceId(UUID.randomUUID().toString());
    }
}
