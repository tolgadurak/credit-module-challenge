package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanFilterRestRequest {
    private int page;
    private int size;
    private Integer installmentCount;
    private Boolean paid;
}
