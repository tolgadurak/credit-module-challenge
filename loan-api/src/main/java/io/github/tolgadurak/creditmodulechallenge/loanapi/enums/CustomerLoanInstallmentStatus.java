package io.github.tolgadurak.creditmodulechallenge.loanapi.enums;

public enum CustomerLoanInstallmentStatus {
    ACTIVE(0),
    PAID(1);

    private final Integer value;

    CustomerLoanInstallmentStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static CustomerLoanInstallmentStatus of(Integer value) {
        for (CustomerLoanInstallmentStatus status : CustomerLoanInstallmentStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
