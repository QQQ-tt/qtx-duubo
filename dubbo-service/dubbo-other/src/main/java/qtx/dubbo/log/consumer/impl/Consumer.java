package qtx.dubbo.log.consumer.impl;

import org.springframework.stereotype.Component;
import qtx.dubbo.log.consumer.ConsumerContext;
import qtx.dubbo.log.consumer.ConsumerInfo;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.rocketmq.util.RocketMQUtils;

/**
 * @author qtx
 * @since 2023/8/3 19:46
 */
@Component
public abstract class Consumer implements ConsumerInfo {

    protected final RocketMQUtils rocketMQUtils;

    protected final RedisUtils redisUtils;

    public Consumer(RocketMQUtils rocketMQUtils, RedisUtils redisUtils) {
        this.rocketMQUtils = rocketMQUtils;
        this.redisUtils = redisUtils;
        register();
    }

    protected void register() {
        ConsumerContext.registerConsumer(this);
    }
}
