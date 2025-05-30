package com.ceawse.authservice.domain.response.exception.information;

import com.ceawse.authservice.domain.constant.Code;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends InformationException {
    public InvalidCredentialsException(String error) {
        super(Code.INVALID_CREDENTIALS, error, HttpStatus.BAD_REQUEST);
    }
}
