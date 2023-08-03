package qtx.dubbo.log.consumer.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.springframework.stereotype.Component;
import qtx.dubbo.config.utils.RocketMQUtils;

/**
 * @author qtx
 * @since 2023/8/3 20:03
 */
@Slf4j
@Component
public class ApiSixConsumer extends Consumer {

    public ApiSixConsumer(RocketMQUtils rocketMQUtils) {
        super(rocketMQUtils);
    }

    @Override
    public void content() throws ClientException {

    }
}
