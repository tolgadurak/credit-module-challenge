package io.github.tolgadurak.creditmodulechallenge.loanapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoanApiBusinessException extends RuntimeException {
    private final String code;
    private final HttpStatus httpStatus;

    public LoanApiBusinessException(String code, HttpStatus httpStatus) {
        super();
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
