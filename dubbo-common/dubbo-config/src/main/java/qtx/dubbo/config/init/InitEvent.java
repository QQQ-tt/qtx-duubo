package qtx.dubbo.config.init;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.producer.TransactionResolution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import qtx.dubbo.config.init.bo.UrlBO;
import qtx.dubbo.config.utils.RedisUtils;
import qtx.dubbo.config.utils.RocketMQUtils;
import qtx.dubbo.java.enums.RocketMQTopicEnums;
import qtx.dubbo.java.info.StaticConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author qtx
 * @since 2023/8/4
 */
@Slf4j
@Component
public class InitEvent {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    private final RedisUtils redisUtils;

    private final RocketMQUtils rocketMQUtils;

    @Value("${spring.application.name}")
    private String serviceName;

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
                    List<UrlBO> list = new ArrayList<>();
                    methodMap.forEach((k, v) -> {
                        if (!"[]".equals(k.getMethodsCondition()
                                .getMethods()
                                .toString())) {
                            assert k.getPathPatternsCondition() != null;
                            Set<PathPattern> set = k.getPathPatternsCondition()
                                    .getPatterns();
                            set.forEach(e -> list.add(UrlBO.builder()
                                    .serviceName(serviceName.split("-")[1])
                                    .name(v.getMethod()
                                            .getName())
                                    .bean(v.getBean()
                                            .toString())
                                    .requestType(k.getMethodsCondition()
                                            .getMethods()
                                            .toString())
                                    .url(e.getPatternString())
                                    .build()));
                        }
                    });
                    return redisUtils.addSetSource(getRedisKey(), list);
                })
                .thenRunAsync(() -> {
                    Long setSize = redisUtils.getSetSize(getRedisKey());
                    try {
                        rocketMQUtils.sendMsg(RocketMQTopicEnums.Url_Transaction,
                                serviceName + "-" + System.currentTimeMillis(), serviceName,
                                messageView -> setSize != null && setSize > 0 ? TransactionResolution.COMMIT : TransactionResolution.ROLLBACK,
                                "1");
                    } catch (ClientException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public String getRedisKey() {
        return StaticConstant.SYS_URL + serviceName + StaticConstant.REDIS_INFO;
    }
}
