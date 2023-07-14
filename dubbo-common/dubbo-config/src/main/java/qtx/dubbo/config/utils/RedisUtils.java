package qtx.dubbo.config.utils;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author qtx
 * @date 2022/8/14 14:54
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final Integer timeOut = 60 * 10;

    /**
     * 添加object类型
     * 默认过期时间600分钟
     *
     * @param msg string
     */
    public void setMsg(String key, Object msg) {
        redisTemplate.opsForValue().set(key, msg, timeOut, TimeUnit.MINUTES);
    }

    /**
     * 添加object类型
     * 自定义过期时间
     *
     * @param msg string
     */
    public void setMsgDiyTimeOut(String key, Object msg, int timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, msg, timeOut, timeUnit);
    }

    public void setHashMsg(String key, String hashKey, Object msg) {
        redisTemplate.opsForHash().put(key, hashKey, msg);
    }

    public void setHashMsgAll(String key, Map<?, ?> msg) {
        redisTemplate.opsForHash().putAll(key, msg);
    }

    public void setHashMsgAllTimeOut(String key, Map<?, ?> msg, long timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, msg);
        redisTemplate.expire(key, timeOut, timeUnit);
    }

    public void setHashMsgTimeOut(String key, String hashKey, Object msg, long timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, msg);
        redisTemplate.expire(key, timeOut, timeUnit);
    }

    public Object getHashMsg(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取hash field 所有key-value
     *
     * @param key redis key
     * @return 键值map集合
     */
    public Map<Object, Object> getHashMsg(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 通过key获取对象
     *
     * @param key key
     * @return object
     */
    public Object getMsg(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 通过key删除
     *
     * @param key key
     */
    public boolean deleteByKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除所有缓存
     */
    public void delAll() {
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        keys.forEach(this::deleteByKey);
    }
}
