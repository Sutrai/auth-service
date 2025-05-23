package com.ceawse.authservice.domain.response.exception.information;

import com.ceawse.authservice.domain.constant.Code;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends InformationException {
    public AuthenticationException(String message) {
        super(Code.UNAUTHORIZED, message, HttpStatus.BAD_REQUEST);
    }
}
