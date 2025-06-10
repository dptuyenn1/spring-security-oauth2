package com.dev.services.impl;

import com.dev.services.RedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class RedisServiceImpl<V> implements RedisService<V> {

    private final ValueOperations<String, V> valueOperations;
    private final HashOperations<String, String, V> hashOperations;

    public RedisServiceImpl(RedisTemplate<String, V> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void set(String key, V value) {
        valueOperations.set(key, value);
    }

    @Override
    public void set(String key, V value, Duration duration) {
        valueOperations.set(key, value, duration);
    }

    @Override
    public void put(String key, String hashKey, V hashValue) {
        hashOperations.put(key, hashKey, hashValue);
    }

    @Override
    public void put(String key, String hashKey, V hashValue, Duration duration) {
        put(key, hashKey, hashValue);
        hashOperations.expire(key, duration, List.of(hashKey));
    }

    @Override
    public V get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public V get(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }
}
