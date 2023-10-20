package qtx.dubbo.log.consumer.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.springframework.stereotype.Component;
import qtx.dubbo.apisix.bo.UrlBO;
import qtx.dubbo.config.utils.NumUtils;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.log.interfaces.ApiSixClient;
import qtx.dubbo.log.interfaces.apisix.ApiEntity;
import qtx.dubbo.log.interfaces.apisix.Plugins;
import qtx.dubbo.log.interfaces.apisix.plugins.ProxyRewrite;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.rocketmq.enums.RocketMQConsumerEnums;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;
import qtx.dubbo.rocketmq.util.RocketMQUtils;

import java.util.Arrays;
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
        setValue.forEach(e -> apiSixClient.addRoute("edd1c9f034335f136f87ad84b625c8f1", NumUtils.numRandom(20, 1, 9), ApiEntity.builder()
                .uri("/" + e.getServiceName() + "/" + e.getUri())
                .name(e.getServiceName() + "-" + e.getUri())
                .methods(Arrays.asList("GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "HEAD",
                        "OPTIONS",
                        "CONNECT",
                        "TRACE",
                        "PURGE"))
                .upstreamId(e.getUpstreamId())
                .plugins(Plugins.builder()
                        .proxyRewrite(ProxyRewrite.builder()
                                .method(e.getRequestType())
                                .regexUri(Arrays.asList("^/" + e.getServiceName() + "/(.*)$", "/$1"))
                                .build())
                        .build())
                .build()));
    }

    public String getRedisKey(String info) {
        return StaticConstant.SYS_URL + info + StaticConstant.REDIS_INFO;
    }
}
