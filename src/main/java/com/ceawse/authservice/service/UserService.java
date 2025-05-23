package com.ceawse.authservice.service;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.ceawse.authservice.domain.entity.UserEntity;

public interface UserService {

    UserEntity saveAndActivate(RegistrationReq req);

    boolean existByEmail(String email);
}
