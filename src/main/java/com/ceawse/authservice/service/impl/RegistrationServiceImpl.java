package com.ceawse.authservice.service.impl;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.ceawse.authservice.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void register(RegistrationReq req, HttpServletResponse response) {

    }

    @Override
    public void checkOtp(String otp, HttpServletRequest request) {

    }
}
