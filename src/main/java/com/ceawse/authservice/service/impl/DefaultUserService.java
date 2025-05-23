package com.ceawse.authservice.service.impl;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.ceawse.authservice.domain.entity.UserEntity;
import com.ceawse.authservice.repository.RoleRepository;
import com.ceawse.authservice.repository.UserRepository;
import com.ceawse.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserEntity saveAndActivate(RegistrationReq req) {
        UserEntity user = new UserEntity();
        user.setNickname(req.getNickname());
        user.setEmail(req.getEmail());
        user.getRoles().add(roleRepository.getDefaultRole());
        user.setActive(true);
        user.setPasswordHash(req.getPassword());
        user.setAdmin(false);
        user.setSuperuser(false);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
