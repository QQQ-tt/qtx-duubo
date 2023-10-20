package qtx.dubbo.log.consumer.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.springframework.stereotype.Component;
import qtx.dubbo.log.impl.SysLogServiceImpl;
import qtx.dubbo.model.entity.log.SysLog;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.rocketmq.enums.RocketMQConsumerEnums;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;
import qtx.dubbo.rocketmq.util.RocketMQUtils;
import qtx.dubbo.service.log.SysLogService;

/**
 * @author qtx
 * @since 2023/8/3 19:50
 */
@Slf4j
@Component
public class LogConsumer extends Consumer {

    private final SysLogService service;

    public LogConsumer(RocketMQUtils rocketMQUtils, RedisUtils redisUtils, SysLogServiceImpl service) {
        super(rocketMQUtils, redisUtils);
        this.service = service;
    }


    @Override
    public void content() {
        try {
            rocketMQUtils.pushConsumer(RocketMQTopicEnums.Log_Normal,
                    RocketMQConsumerEnums.Log_consumer_group,
                    messageView -> {
                        try {
                            SysLog entity = RocketMQUtils.getEntity(messageView, SysLog.class);
                            service.save(entity);
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
