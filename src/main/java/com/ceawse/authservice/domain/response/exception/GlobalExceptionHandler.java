package com.ceawse.authservice.domain.response.exception;

import com.ceawse.authservice.domain.response.error.Data;
import com.ceawse.authservice.domain.response.error.ErrorResponse;
import com.ceawse.authservice.domain.response.exception.information.InformationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final boolean enableStacktrace;

    public GlobalExceptionHandler(
            @Value("${server.error.include-stacktrace}") String includeStackTrace) {
        this.enableStacktrace = ("always").equals(includeStackTrace);
    }

    @ExceptionHandler(InformationException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(InformationException ex, HttpServletRequest request){

        logRequestException(request, ex);

        return new ResponseEntity<>(ErrorResponse.builder()
                .data(Data.builder()
                        .error(ex.getError())
                        .method(request.getMethod())
                        .request(request.getRequestURI())
                        .build())
                .informative(true)
                .success(false).status(ex.getHttpStatus().value()).build(),
                ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder()
                        .data(Data.builder()
                                .error("Internal Server Error")
                                .techMessage(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()))
                                .method(request.getMethod())
                                .request(request.getRequestURI())
                                .build())
                        .stacktrace(enableStacktrace ? ex.getStackTrace() : null)
                        .success(false).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private void logRequestException(HttpServletRequest request, Exception exception) {
        log.debug("Unexpected exception processing request: " + request.getRequestURI());
        log.error("Exception: ", exception);
    }
}
