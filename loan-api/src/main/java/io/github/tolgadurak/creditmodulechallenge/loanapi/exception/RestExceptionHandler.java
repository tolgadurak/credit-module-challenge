package io.github.tolgadurak.creditmodulechallenge.loanapi.exception;

import io.github.tolgadurak.creditmodulechallenge.loanapi.exception.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
    private static final String GENERAL_ERROR_CODE = "errors.generalError";
    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> generalExceptionHandler(Exception e, WebRequest webRequest) {
        log.error("An exception occurred", e);
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(ErrorResponse.Error.builder()
                .code(GENERAL_ERROR_CODE)
                .message(this.buildMessage(GENERAL_ERROR_CODE, webRequest.getLocale()))
                .build());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(errors)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler(LoanApiBusinessException.class)
    public ResponseEntity<Object> loanApiBusinessException(LoanApiBusinessException e, WebRequest webRequest) {
        log.error("Loan api business exception", e);
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(ErrorResponse.Error.builder()
                .code(e.getCode())
                .message(this.buildMessage(e.getCode(), webRequest.getLocale()))
                .build());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(errors)
                .status(e.getHttpStatus().value())
                .build();
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), e.getHttpStatus(), webRequest);
    }

    private String buildMessage(String code, Locale locale) {
        try {
            return messageSource.getMessage(code, new Object[]{}, locale);
        } catch (Exception e) {
            log.error("Error while getting message from properties", e);
        }
        return messageSource.getMessage(GENERAL_ERROR_CODE, new Object[]{}, locale);
    }
}
