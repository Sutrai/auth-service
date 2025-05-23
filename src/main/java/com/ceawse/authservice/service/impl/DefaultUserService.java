package com.ceawse.authservice.service.impl;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.ceawse.authservice.domain.entity.UserEntity;
import com.ceawse.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    @Override
    public UserEntity saveAndActivate(RegistrationReq req) {
        return null;
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }
}
