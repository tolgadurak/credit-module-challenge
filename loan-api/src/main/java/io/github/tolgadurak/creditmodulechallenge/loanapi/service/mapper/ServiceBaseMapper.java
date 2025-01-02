package io.github.tolgadurak.creditmodulechallenge.loanapi.service.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.AbstractEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.EntityStatus;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.BaseResponse;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public class ServiceBaseMapper {
    @BeforeMapping
    public void beforeAbstractEntityMapping(@MappingTarget AbstractEntity entity) {
        entity.setEntityStatus(EntityStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setReferenceId(UUID.randomUUID().toString());
    }

    @BeforeMapping
    public void beforeBaseResponseMapping(AbstractEntity abstractEntity, @MappingTarget BaseResponse baseResponse) {
        baseResponse.setEntityStatus(abstractEntity.getEntityStatus());
        baseResponse.setCreatedDate(abstractEntity.getCreatedDate());
        baseResponse.setUpdatedDate(abstractEntity.getUpdatedDate());
        baseResponse.setReferenceId(abstractEntity.getReferenceId());
    }
}
