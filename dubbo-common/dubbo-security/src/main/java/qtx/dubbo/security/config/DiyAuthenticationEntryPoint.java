package qtx.dubbo.security.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class DiyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.info("user info error {}", DataEnums.ACCESS_DENIED);
        CommonMethod.failed(request,response, DataEnums.ACCESS_DENIED);
    }
}
