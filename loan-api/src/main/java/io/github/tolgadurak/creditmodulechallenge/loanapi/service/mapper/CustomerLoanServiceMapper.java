package io.github.tolgadurak.creditmodulechallenge.loanapi.service.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanInstallmentEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanLimitEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanInstallmentCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanLimitCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanInstallmentQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ServiceBaseMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerLoanServiceMapper {

    CustomerLoanEntity toEntity(CustomerLoanCreateRequest customerLoanCreateRequest);

    CustomerLoanInstallmentEntity toEntity(CustomerLoanInstallmentCreateRequest customerLoanInstallmentCreateRequest);

    CustomerLoanLimitEntity toEntity(CustomerLoanLimitCreateRequest customerLoanInstallmentCreateRequest);

    CustomerLoanQueryResponse toModel(CustomerLoanEntity customerLoanEntity);

    CustomerLoanInstallmentQueryResponse toModel(CustomerLoanInstallmentEntity customerLoanInstallmentEntity);

    List<CustomerLoanQueryResponse> toCustomerLoanQueryResponseList(List<CustomerLoanEntity> customerLoanEntities);

    List<CustomerLoanInstallmentQueryResponse> toCustomerLoanInstallmentQueryResponseList(List<CustomerLoanInstallmentEntity> customerLoanInstallmentEntities);
}
