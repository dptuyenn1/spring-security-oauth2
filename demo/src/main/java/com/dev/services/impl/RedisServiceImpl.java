package com.dev.services.impl;

import com.dev.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl<T> implements RedisService<T> {

    private final RedisTemplate<String, T> redisTemplate;

    @Override
    public void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, T value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
