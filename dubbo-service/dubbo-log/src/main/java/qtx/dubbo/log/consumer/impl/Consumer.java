package qtx.dubbo.log.consumer.impl;

import org.springframework.stereotype.Component;
import qtx.dubbo.config.utils.RocketMQUtils;
import qtx.dubbo.log.consumer.ConsumerContext;
import qtx.dubbo.log.consumer.ConsumerInfo;

/**
 * @author qtx
 * @since 2023/8/3 19:46
 */
@Component
public abstract class Consumer implements ConsumerInfo {

    protected final RocketMQUtils rocketMQUtils;

    public Consumer(RocketMQUtils rocketMQUtils) {
        this.rocketMQUtils = rocketMQUtils;
        register();
    }

    protected void register() {
        ConsumerContext.registerConsumer(this);
    }
}
