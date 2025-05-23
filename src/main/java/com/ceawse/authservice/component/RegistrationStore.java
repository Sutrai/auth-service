package com.ceawse.authservice.component;

import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RegistrationStore {

    void save(RegistrationReq req, String sessionId) throws JsonProcessingException;

    RegistrationReq take(String sessionId) throws JsonProcessingException;
}
