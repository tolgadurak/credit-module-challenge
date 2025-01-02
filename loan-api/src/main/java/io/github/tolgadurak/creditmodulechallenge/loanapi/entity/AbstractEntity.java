package io.github.tolgadurak.creditmodulechallenge.loanapi.entity;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.EntityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String referenceId;

    @Column
    private EntityStatus entityStatus;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;
}
