package booking_service.service;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisSeatLockService {
    private final StringRedisTemplate redisTemplate;

    public RedisSeatLockService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
