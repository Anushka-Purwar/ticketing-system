package booking_service.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisSeatLockService {
    private final StringRedisTemplate redisTemplate;

    public RedisSeatLockService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    private String generateSeatLockKey(Long showId, Long seatId) {
        return "seat:" + showId + ":" + seatId;
    }
    public boolean lockSeat(Long showId, Long seatId, String userName){
        String key = generateSeatLockKey(showId,seatId);
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(key,userName, Duration.ofMinutes(5));
        return Boolean.TRUE.equals(locked);
    }

    public boolean isSeatLocked(Long showId, Long seatId){
        String key = generateSeatLockKey(showId,seatId);
        Boolean exists = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }

    public boolean unlockSeat(Long showId, Long seatId) {
        String key = generateSeatLockKey(showId,seatId);
        Boolean deleted = redisTemplate.delete(key);
        return Boolean.TRUE.equals(deleted);
    }
}
