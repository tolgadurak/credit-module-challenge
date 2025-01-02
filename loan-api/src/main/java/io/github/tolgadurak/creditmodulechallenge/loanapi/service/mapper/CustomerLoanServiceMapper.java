package io.github.tolgadurak.creditmodulechallenge.loanapi.service.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanInstallmentEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanLimitEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.mapper.EntityBaseMapper;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanInstallmentCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanLimitCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {EntityBaseMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerLoanServiceMapper {

    CustomerLoanEntity toEntity(CustomerLoanCreateRequest customerLoanCreateRequest);

    CustomerLoanInstallmentEntity toEntity(CustomerLoanInstallmentCreateRequest customerLoanInstallmentCreateRequest);

    CustomerLoanLimitEntity toEntity(CustomerLoanLimitCreateRequest customerLoanInstallmentCreateRequest);
}
