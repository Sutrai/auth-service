package com.ceawse.authservice.component.impl;

import com.ceawse.authservice.component.RegistrationStore;
import com.ceawse.authservice.domain.api.registration.RegistrationReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class RedisRegistrationStore implements RegistrationStore {

    private static final String SESSION_ID_TO_REG_DATA = "registration_store:session_id_to_reg_data:";

    private final int expiresIn;
    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> store;
    private final ObjectMapper objectMapper;

    public RedisRegistrationStore(int expiresIn, StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.expiresIn = expiresIn;
        this.redisTemplate = redisTemplate;
        this.store = redisTemplate.opsForValue();
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(RegistrationReq req, String sessionId) throws JsonProcessingException {
        String stringDto = objectMapper.writeValueAsString(req);
        store.set(SESSION_ID_TO_REG_DATA + sessionId, stringDto, expiresIn, TimeUnit.SECONDS);
    }

    @Override
    public RegistrationReq take(String sessionId) throws JsonProcessingException {
        String stringDto = store.getAndDelete(SESSION_ID_TO_REG_DATA + sessionId);

        // TODO добавить шифрование пороля при сохранении в redis

        return objectMapper.readValue(stringDto, RegistrationReq.class);
    }
}
