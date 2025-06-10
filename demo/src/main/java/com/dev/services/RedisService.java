package com.dev.services;

public interface RedisService<V> {

    void set(String key, V value);

    void set(String key, V value, long expiredAt);

    void put(String key, String hashKey, V hashValue);

    void put(String key, String hashKey, V hashValue, long expiredAt);

    V get(String key);

    V get(String key, String hashKey);
}
