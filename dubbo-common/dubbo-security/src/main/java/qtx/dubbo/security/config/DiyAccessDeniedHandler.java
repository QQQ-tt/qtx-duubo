package qtx.dubbo.security.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;

import java.io.IOException;

/**
 * @author qtx
 * @since 2022/8/31
 */
@Component
@Slf4j
@ConditionalOnProperty(name = "spring.security", havingValue = "true")
public class DiyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.info("user info error {}", DataEnums.ACCESS_DENIED);
        CommonMethod.failed(request,response, DataEnums.ACCESS_DENIED);
    }
}
