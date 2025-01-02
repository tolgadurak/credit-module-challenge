package io.github.tolgadurak.creditmodulechallenge.loanapi.facade.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.BaseResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.BaseRestResponse;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public class FacadeBaseMapper {
    @BeforeMapping
    public void beforeBaseRestResponseMapping(BaseResponse baseResponse, @MappingTarget BaseRestResponse baseRestResponse) {
        baseRestResponse.setEntityStatus(baseResponse.getEntityStatus());
        baseRestResponse.setCreatedDate(baseResponse.getCreatedDate());
        baseRestResponse.setUpdatedDate(baseResponse.getUpdatedDate());
        baseRestResponse.setReferenceId(baseResponse.getReferenceId());
    }
}
