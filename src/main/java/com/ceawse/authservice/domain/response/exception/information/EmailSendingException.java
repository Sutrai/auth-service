package com.ceawse.authservice.domain.response.exception.information;

import com.ceawse.authservice.domain.constant.Code;
import org.springframework.http.HttpStatus;

public class EmailSendingException extends InformationException {
    public EmailSendingException(String error) {
        super(Code.EMAIL_SENDING_EXCEPTION, error, HttpStatus.BAD_REQUEST);
    }
}
