package qtx.dubbo.security.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import qtx.dubbo.config.utils.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;

import java.io.IOException;

/**
 * @author qtx
 * @since 2022/8/31
 */
@Component
@Slf4j
public class DiyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final CommonMethod commonMethod;

    public DiyAuthenticationEntryPoint(CommonMethod commonMethod) {
        this.commonMethod = commonMethod;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("user info error {}", DataEnums.ACCESS_DENIED);
        commonMethod.failed(response, DataEnums.ACCESS_DENIED);
    }
}
