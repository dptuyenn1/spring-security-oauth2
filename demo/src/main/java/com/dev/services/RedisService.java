package com.dev.services;

import java.time.Duration;

public interface RedisService<V> {

    void set(String key, V value);

    void set(String key, V value, Duration duration);

    void put(String key, String hashKey, V hashValue);

    void put(String key, String hashKey, V hashValue, Duration duration);

    V get(String key);

    V get(String key, String hashKey);
}
