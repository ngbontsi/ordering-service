package com.example.ordering.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IdempotencyService {
    private final RedisTemplate<String, String> redis;
    private final Duration TTL = Duration.ofHours(24);

    public IdempotencyService(RedisTemplate<String, String> redis) { this.redis = redis; }

    public boolean claim(String tenant, String key) {
        if (key == null) return false;
        String redisKey = "idem:" + tenant + ":" + key;
        Boolean success = redis.opsForValue().setIfAbsent(redisKey, "1", TTL);
        return Boolean.TRUE.equals(success);
    }
}
