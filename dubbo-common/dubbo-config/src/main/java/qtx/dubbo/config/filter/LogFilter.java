package qtx.dubbo.config.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import qtx.dubbo.config.bo.LogBO;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;
import qtx.dubbo.rocketmq.util.RocketMQUtils;

/**
 * @author qtx
 * @since 2023/10/20
 */
@Slf4j
@Order(2)
@WebFilter("/*")
@ConditionalOnProperty(name = {"rocketmq.enable", "log.mysql.enable"}, havingValue = "true")
public class LogFilter extends OncePerRequestFilter {

    private final RocketMQUtils rocketMQUtils;

    public LogFilter(RocketMQUtils utils) {
        this.rocketMQUtils = utils;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
                                    FilterChain filterChain) {
        rocketMQUtils.sendAsyncMsg(RocketMQTopicEnums.Log_Normal, String.valueOf(System.currentTimeMillis()),
                JSON.toJSONString(LogBO.logBOThreadLocal.get()));
        LogBO.logBOThreadLocal.remove();
        filterChain.doFilter(request, response);
    }
}
