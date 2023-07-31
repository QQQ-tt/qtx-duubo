package qtx.dubbo.config.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.apis.ClientException;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import qtx.dubbo.config.utils.CommonMethod;
import qtx.dubbo.config.utils.JwtUtils;
import qtx.dubbo.config.utils.RedisUtils;
import qtx.dubbo.config.utils.RocketMQUtils;
import qtx.dubbo.java.enums.AuthUrlEnums;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.enums.LogUrlEnums;
import qtx.dubbo.java.enums.RocketMQTopicEnums;
import qtx.dubbo.java.info.StaticConstant;

import java.io.IOException;
import java.util.Map;

/**
 * @author qtx
 * @since 2022/11/8 10:30
 */
@Slf4j
@Order(1)
@WebFilter("/*")
public class AuthFilter extends OncePerRequestFilter {

    private final CommonMethod commonMethod;

    private final RedisUtils redisUtils;

    private final RocketMQUtils rocketMQUtils;

    public AuthFilter(CommonMethod commonMethod, RedisUtils redisUtils, RocketMQUtils rocketMQUtils) {
        this.commonMethod = commonMethod;
        this.redisUtils = redisUtils;
        this.rocketMQUtils = rocketMQUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        commonMethod.setIp(ip);

        if (AuthUrlEnums.authPath(uri)) {
            RequestWrapper requestWrapper = getRequestWrapper(request, uri, null, ip);
            filterChain.doFilter(requestWrapper == null ? request : requestWrapper, response);
            return;
        }

        String token = request.getHeader(StaticConstant.TOKEN);
        if (StringUtils.isBlank(token)) {
            commonMethod.failed(response, DataEnums.ACCESS_DENIED);
            return;
        }

        if (!JwtUtils.validateToken(token)) {
            commonMethod.failed(response, DataEnums.TOKEN_IS_ILLEGAL);
            return;
        }
        if (!JwtUtils.isTokenExpired(token)) {
            commonMethod.failed(response, DataEnums.TOKEN_LOGIN_EXPIRED);
            return;
        }
        String userCode = JwtUtils.getUsernameFromToken(token);
        Map<Object, Object> redisUser = redisUtils.getHashMsg(
                StaticConstant.LOGIN_USER + userCode + StaticConstant.REDIS_INFO);
        if (redisUser == null) {
            commonMethod.failed(response, DataEnums.TOKEN_LOGIN_EXPIRED);
            return;
        }
        if (!redisUser.get("userCode")
                .toString()
                .equals(userCode)) {
            commonMethod.failed(response, DataEnums.TOKEN_IS_ILLEGAL);
            return;
        }

        RequestWrapper requestWrapper = getRequestWrapper(request, uri, userCode, ip);
        commonMethod.setUserCode(userCode);

        filterChain.doFilter(requestWrapper == null ? request : requestWrapper, response);
    }


    private RequestWrapper getRequestWrapper(HttpServletRequest request, String uri, String userCode, String ip) throws IOException {
        RequestWrapper requestWrapper = null;
        String method = request.getMethod();
        String json, param;
        if (!LogUrlEnums.logPath(uri)) {
            requestWrapper = new RequestWrapper(request);
            String s = JSON.toJSONString(requestWrapper.getBodyString());
            json = s.replaceAll(" ", "")
                    .replaceAll("\\\\n", "")
                    .replaceAll("\\\\", "");
            param = JSON.toJSONString(request.getParameterMap());
            log.info("userCode:{};request:[method:{},path:{},json:{},param:{}]", userCode,
                    method,
                    uri,
                    json,
                    param);
            try {
                rocketMQUtils.sendAsyncMsg(RocketMQTopicEnums.Log_Normal, String.valueOf(System.currentTimeMillis()),
                        JSON.toJSONString(
                                LogBO.builder()
                                        .userCode(userCode)
                                        .method(method)
                                        .path(uri)
                                        .json(json)
                                        .param(param)
                                        .createBy(ip)
                                        .build()));
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
        }
        return requestWrapper;
    }
}
