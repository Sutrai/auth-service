package com.ceawse.authservice.domain.response.exception.information;

import com.ceawse.authservice.domain.constant.Code;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends InformationException {
    public UserAlreadyExistsException(String message) {
        super(Code.USER_ALREADY_EXISTS, message, HttpStatus.CONFLICT);
    }
}
