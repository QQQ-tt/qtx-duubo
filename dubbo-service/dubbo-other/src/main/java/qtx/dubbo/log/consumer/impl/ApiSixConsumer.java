package qtx.dubbo.log.consumer.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.springframework.stereotype.Component;
import qtx.dubbo.config.init.bo.UrlBO;
import qtx.dubbo.config.utils.RedisUtils;
import qtx.dubbo.config.utils.RocketMQUtils;
import qtx.dubbo.java.enums.RocketMQConsumerEnums;
import qtx.dubbo.java.enums.RocketMQTopicEnums;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.log.interfaces.ApiSixClient;

import java.util.Set;

/**
 * @author qtx
 * @since 2023/8/3 20:03
 */
@Slf4j
@Component
public class ApiSixConsumer extends Consumer {

    private final ApiSixClient apiSixClient;

    public ApiSixConsumer(RocketMQUtils rocketMQUtils, RedisUtils redisUtils, ApiSixClient apiSixClient) {
        super(rocketMQUtils, redisUtils);
        this.apiSixClient = apiSixClient;
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
                            registerApi(getRedisKey(info));
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

    public void registerApi(String redisKey) {
        Set<UrlBO> setValue = redisUtils.getSetValue(redisKey, UrlBO.class);
        System.out.println(setValue);
    }

    public String getRedisKey(String info) {
        return StaticConstant.SYS_URL + info + StaticConstant.REDIS_INFO;
    }
}
