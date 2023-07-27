package qtx.dubbo.log.consumer;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import qtx.dubbo.config.utils.RocketMQUtils;
import qtx.dubbo.java.enums.RocketMQConsumerEnums;
import qtx.dubbo.java.enums.RocketMQTopicEnums;
import qtx.dubbo.log.impl.SysLogServiceImpl;
import qtx.dubbo.model.entity.log.SysLog;

import java.util.concurrent.CompletableFuture;

/**
 * @author qtx
 * @since 2023/7/27
 */
@Slf4j
@Component
public class DiyConsumer {

    private final RocketMQUtils rocketMQUtils;

    private final SysLogServiceImpl service;

    public DiyConsumer(RocketMQUtils rocketMQUtils, SysLogServiceImpl service) {
        this.rocketMQUtils = rocketMQUtils;
        this.service = service;
    }

    @SneakyThrows
    @PostConstruct
    public void initConsumer() {
        logConsumer();
    }

    @Async
    public void logConsumer() throws ClientException {
        CompletableFuture.completedFuture(rocketMQUtils.pushConsumer(RocketMQTopicEnums.Log_Normal,
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
                }));
    }
}
