package com.dev.services;

import java.time.Duration;

public interface RedisService<T> {

    void set(String key, T value);

    void set(String key, T value, Duration duration);

    T get(String key);
}
