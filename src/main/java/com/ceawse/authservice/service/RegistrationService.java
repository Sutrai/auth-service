package com.ceawse.authservice.service;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RegistrationService {

    void register(RegistrationReq req, HttpServletResponse response);

    void checkOtp(String otp, HttpServletRequest request);
}
