package qtx.dubbo.log.consumer.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.springframework.stereotype.Component;
import qtx.dubbo.config.utils.RedisUtils;
import qtx.dubbo.config.utils.RocketMQUtils;
import qtx.dubbo.java.enums.RocketMQConsumerEnums;
import qtx.dubbo.java.enums.RocketMQTopicEnums;

/**
 * @author qtx
 * @since 2023/8/3 20:03
 */
@Slf4j
@Component
public class ApiSixConsumer extends Consumer {

    public ApiSixConsumer(RocketMQUtils rocketMQUtils, RedisUtils redisUtils) {
        super(rocketMQUtils, redisUtils);
    }

    @Override
    public void content() {
        try {
            rocketMQUtils.pushConsumer(RocketMQTopicEnums.Url_Transaction,
                    RocketMQConsumerEnums.Url_consumer_group,
                    messageView -> {
                        try {
                            String info = RocketMQUtils.getEntity(messageView, String.class);
                            redisUtils.addMsg(String.valueOf(messageView.getMessageId()), info, null);
                            // todo 注册逻辑待实现
                            log.info("Consume message successfully, messageId={}", messageView.getMessageId());
                            return ConsumeResult.SUCCESS;
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            return ConsumeResult.FAILURE;
                        }
                    });
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
}
