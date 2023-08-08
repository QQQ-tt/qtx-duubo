package qtx.dubbo.log.consumer;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author qtx
 * @since 2023/8/8
 */
@Component
@ConditionalOnProperty(name = "rocketmq.enable",havingValue = "true")
public class InitConsumer {

    @PostConstruct
    public void initConsumer(){
        ConsumerContext.getConsumerInfoList().forEach(ConsumerInfo::content);
    }
}
