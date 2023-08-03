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

    @SneakyThrows
    @PostConstruct
    public void initConsumer() {
        ConsumerContext.getConsumerInfoList().forEach(consumerInfo -> {
            try {
                consumerInfo.content();
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
