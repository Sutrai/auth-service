package com.ceawse.authservice.domain.response.exception.service;

import com.ceawse.authservice.domain.constant.Code;
import org.springframework.http.HttpStatus;

public class CryptoException extends ServiceException {
  public CryptoException(String message) {
    super(Code.INVALID_KEY_SPEC, message, HttpStatus.BAD_REQUEST);
  }
}
