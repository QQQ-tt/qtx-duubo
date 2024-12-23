package qtx.dubbo.redis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    private final RedisTemplate<String, Object> redisTemplate;

    private final Integer TIMEOUT = 60 * 10;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加object类型
     * 默认过期时间600分钟
     *
     * @param msg string
     */
    public void addMsg(String key, Object msg, Integer timeOut) {
        redisTemplate.opsForValue()
                .set(key, msg, timeOut != null ? timeOut : TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 添加List类型
     * @param key key
     * @param msg msg
     */
    public void addListMsg(String key, Object msg) {
        redisTemplate.opsForList()
                .rightPush(key, msg);
        redisTemplate.expire(key, 300, TimeUnit.MINUTES);
    }

    /**
     * 添加object类型
     * 自定义过期时间
     *
     * @param msg string
     */
    public void addMsgDiyTimeOut(String key, Object msg, int timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForValue()
                .set(key, msg, timeOut, timeUnit);
    }

    public void addHashMsg(String key, String hashKey, Object msg) {
        redisTemplate.opsForHash()
                .put(key, hashKey, msg);
    }

    public void addHashMsgAll(String key, Map<?, ?> msg) {
        redisTemplate.opsForHash()
                .putAll(key, msg);
    }

    public void addHashMsgAllTimeOut(String key, Map<?, ?> msg, long timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForHash()
                .putAll(key, msg);
        redisTemplate.expire(key, timeOut, timeUnit);
    }

    public void addHashMsgTimeOut(String key, String hashKey, Object msg, long timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForHash()
                .put(key, hashKey, msg);
        redisTemplate.expire(key, timeOut, timeUnit);
    }

    public boolean addSetSource(String key, Object... objects) {
        redisTemplate.opsForSet()
                .add(key, objects);
        return Boolean.TRUE.equals(redisTemplate.expire(key, TIMEOUT, TimeUnit.SECONDS));
    }

    public Long getSetSize(String key) {
        return redisTemplate.opsForSet()
                .size(key);
    }

    public <E> Set<E> getSetValue(String key, Class<E> expectedClass) {
        Set<Object> members = redisTemplate.opsForSet()
                .members(key);
        Set<E> result = new HashSet<>();
        if (members != null){
            for (Object member : members) {
                if (expectedClass.isAssignableFrom(member.getClass())) {
                    result.add(expectedClass.cast(member));
                }
            }
        }
        return result;
    }

    public Object getHashMsg(String key, String hashKey) {
        return redisTemplate.opsForHash()
                .get(key, hashKey);
    }

    /**
     * 获取hash field 所有key-value
     *
     * @param key redis key
     * @return 键值map集合
     */
    public Map<Object, Object> getHashMsg(String key) {
        return redisTemplate.opsForHash()
                .entries(key);
    }

    /**
     * 通过key获取对象
     *
     * @param key key
     * @return object
     */
    public Object getMsg(String key) {
        return redisTemplate.opsForValue()
                .get(key);
    }

    /**
     * 通过key删除
     *
     * @param key key
     */
    public void deleteByKey(String key) {
        redisTemplate.delete(key);
    }

    public void deleteByKeys(String key) {
        Set<String> keys = redisTemplate.keys(key);
        keys.forEach(this::deleteByKey);
    }

    /**
     * 删除所有缓存
     */
    public void delAll() {
        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(this::deleteByKey);
    }
}
