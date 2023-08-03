package qtx.dubbo.log.consumer;

import org.apache.rocketmq.client.apis.ClientException;
import org.springframework.scheduling.annotation.Async;

/**
 * @author qtx
 * @since 2023/8/3 19:36
 */
public interface ConsumerInfo {

    @Async
    void content() throws ClientException;
}
