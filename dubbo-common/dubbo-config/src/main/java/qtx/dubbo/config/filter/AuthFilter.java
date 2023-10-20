package qtx.dubbo.config.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import qtx.dubbo.config.bo.LogBO;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.enums.AuthUrlEnums;
import qtx.dubbo.java.enums.LogUrlEnums;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;

import java.io.IOException;

/**
 * @author qtx
 * @since 2022/11/8 10:30
 */
@Slf4j
@Order(1)
@WebFilter("/*")
public class AuthFilter extends OncePerRequestFilter {

    private final CommonMethod commonMethod;

    public AuthFilter(CommonMethod commonMethod) {
        this.commonMethod = commonMethod;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String uri = commonMethod.getUri();
        String ip = commonMethod.getIp();
        String userCode = commonMethod.getUserCode();

        if (AuthUrlEnums.authPath(uri)) {
            RequestWrapper requestWrapper = getRequestWrapper(request, uri, null, ip);
            filterChain.doFilter(requestWrapper == null ? request : requestWrapper, response);
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
            setLogBO(LogBO.builder()
                    .userCode(userCode)
                    .method(method)
                    .path(uri)
                    .json(json)
                    .param(param)
                    .createBy(ip)
                    .build());
        }
        return requestWrapper;
    }

    public void setLogBO(LogBO logBO){
        LogBO.logBOThreadLocal.set(logBO);
    }

    public void removeLogBO(){
        LogBO.logBOThreadLocal.remove();
    }
}
