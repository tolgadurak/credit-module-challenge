package io.github.tolgadurak.creditmodulechallenge.loanapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanCreateResponse {
    private String referenceId;
}
