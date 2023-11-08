package qtx.dubbo.apisix.init;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.producer.TransactionResolution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import qtx.dubbo.apisix.bo.UrlBO;
import qtx.dubbo.java.enums.ApiEnum;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;
import qtx.dubbo.rocketmq.util.RocketMQUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author qtx
 * @since 2023/8/4
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "rocketmq.enable",havingValue = "true")
public class InitEvent {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    private final RedisUtils redisUtils;

    private final RocketMQUtils rocketMQUtils;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${apisix.upstream-id}")
    private String upstreamId;

    public InitEvent(RequestMappingHandlerMapping requestMappingHandlerMapping, RedisUtils redisUtils, RocketMQUtils rocketMQUtils) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.redisUtils = redisUtils;
        this.rocketMQUtils = rocketMQUtils;
    }

    @PostConstruct
    public void urlInitToApisix() {
        CompletableFuture.supplyAsync(() -> {
                    redisUtils.deleteByKey(getRedisKey());
                    Map<RequestMappingInfo, HandlerMethod> methodMap = requestMappingHandlerMapping.getHandlerMethods();
                    methodMap.forEach((k, v) -> {
                        if (!"[]".equals(k.getMethodsCondition()
                                .getMethods()
                                .toString())) {
                            assert k.getPathPatternsCondition() != null;
                            Set<PathPattern> set = k.getPathPatternsCondition()
                                    .getPatterns();
                            set.forEach(e -> {
                                if (!(e == null || ApiEnum.authPath(e.getPatternString()))) {
                                    redisUtils.addSetSource(getRedisKey(), UrlBO.builder()
                                            .serviceName(serviceName.split("-")[1])
                                            .name(v.getMethod()
                                                    .getName())
                                            .bean(v.getBean()
                                                    .toString())
                                            .requestType(k.getMethodsCondition()
                                                    .getMethods()
                                                    .toString())
                                            .uri(e.getPatternString())
                                            .upstreamId(upstreamId)
                                            .build());
                                }
                            });
                        }
                    });
                    return 1;
                })
                .thenRunAsync(() -> {
                    Long setSize = redisUtils.getSetSize(getRedisKey());
                    try {
                        rocketMQUtils.sendMsg(RocketMQTopicEnums.Url_Transaction,
                                serviceName + "-" + System.currentTimeMillis(), serviceName,
                                messageView -> serviceName.equals(redisUtils.getMsg(String.valueOf(
                                        messageView.getMessageId()))) ? TransactionResolution.COMMIT : TransactionResolution.ROLLBACK,
                                "1", setSize != null && setSize > 0);
                    } catch (ClientException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public String getRedisKey() {
        return StaticConstant.SYS_URL + serviceName + StaticConstant.REDIS_INFO;
    }
}
