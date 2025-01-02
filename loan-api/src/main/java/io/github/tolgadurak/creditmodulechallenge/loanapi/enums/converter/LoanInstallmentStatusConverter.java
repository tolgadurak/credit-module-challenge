package io.github.tolgadurak.creditmodulechallenge.loanapi.enums.converter;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.CustomerLoanInstallmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LoanInstallmentStatusConverter implements AttributeConverter<CustomerLoanInstallmentStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CustomerLoanInstallmentStatus attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public CustomerLoanInstallmentStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? CustomerLoanInstallmentStatus.of(dbData) : null;
    }
}
