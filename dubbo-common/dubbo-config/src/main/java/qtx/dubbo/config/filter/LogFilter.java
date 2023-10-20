package qtx.dubbo.config.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import qtx.dubbo.config.bo.LogBO;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;
import qtx.dubbo.rocketmq.util.RocketMQUtils;

import java.io.IOException;

/**
 * @author qtx
 * @since 2023/10/20
 */
@Slf4j
@Order(2)
@WebFilter("/*")
public class LogFilter extends OncePerRequestFilter {

    private final RocketMQUtils rocketMQUtils;

    public LogFilter(RocketMQUtils rocketMQUtils) {
        this.rocketMQUtils = rocketMQUtils;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        rocketMQUtils.sendAsyncMsg(RocketMQTopicEnums.Log_Normal, String.valueOf(System.currentTimeMillis()),
                JSON.toJSONString(LogBO.logBOThreadLocal.get()));
        filterChain.doFilter(request, response);
    }
}