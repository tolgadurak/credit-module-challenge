package io.github.tolgadurak.creditmodulechallenge.loanapi.enums;

public enum EntityStatus {
    ACTIVE(0),
    PASSIVE(1),
    DELETED(2);

    private final Integer value;

    EntityStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static EntityStatus of(Integer value) {
        for (EntityStatus status : EntityStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
