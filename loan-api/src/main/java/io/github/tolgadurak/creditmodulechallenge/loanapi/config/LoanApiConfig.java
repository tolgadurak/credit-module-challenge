package io.github.tolgadurak.creditmodulechallenge.loanapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@ConfigurationProperties(value = "loan-api.config")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApiConfig {
    private List<Integer> allowedInstallments;
    private BigDecimal minInterestRate;
    private BigDecimal maxInterestRate;
    private Integer maxNumberOfMonthsCanBePaid;
}
