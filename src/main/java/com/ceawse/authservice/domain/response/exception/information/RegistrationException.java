package com.ceawse.authservice.domain.response.exception.information;

import com.ceawse.authservice.domain.constant.Code;
import org.springframework.http.HttpStatus;

public class RegistrationException extends InformationException {
  public RegistrationException(String error) {
    super(Code.VALIDATE_ERROR, error, HttpStatus.BAD_REQUEST);
  }
}
