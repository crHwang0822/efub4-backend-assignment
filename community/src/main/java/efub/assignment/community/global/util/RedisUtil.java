package efub.assignment.community.global.util;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    private final RedisTemplate<String,Object> redisTemplate;

    public RedisUtil(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String key, String data){
        ValueOperations<String,Object> values = redisTemplate.opsForValue();
        values.set(key,data);
    }

    public void setValues(String key, String data, Duration duration){
        ValueOperations<String,Object> values = redisTemplate.opsForValue();
        values.set(key,data,duration);
    }

    public Object getValues(String key){
        ValueOperations<String,Object> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key){
        redisTemplate.delete(key);
    }

}
