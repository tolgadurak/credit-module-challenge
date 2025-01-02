package io.github.tolgadurak.creditmodulechallenge.loanapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApiConfig {
    private List<Integer> allowedInstallments;
    private BigDecimal minimumInterestRate;
    private BigDecimal maximumInterestRate;
}
