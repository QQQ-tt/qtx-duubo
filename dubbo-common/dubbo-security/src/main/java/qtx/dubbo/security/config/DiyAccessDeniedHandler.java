package qtx.dubbo.security.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import qtx.dubbo.config.utils.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;

import java.io.IOException;

/**
 * @author: QTX
 * @Since: 2022/8/31
 */
@Component
@Slf4j
public class DiyAccessDeniedHandler implements AccessDeniedHandler {

    private final CommonMethod commonMethod;

    public DiyAccessDeniedHandler(CommonMethod commonMethod) {
        this.commonMethod = commonMethod;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("user info error {}", DataEnums.ACCESS_DENIED);
        commonMethod.failed(response, DataEnums.ACCESS_DENIED);
    }
}
