package io.github.tolgadurak.creditmodulechallenge.loanapi.model.response;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.EntityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String referenceId;
    private EntityStatus entityStatus;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
