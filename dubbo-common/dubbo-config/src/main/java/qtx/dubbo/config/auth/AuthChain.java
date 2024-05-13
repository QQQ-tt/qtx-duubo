package qtx.dubbo.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.util.JwtUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author qtx
 * @since 2024/5/13 10:36
 */
@Component
@ConditionalOnProperty(name = "spring.security", havingValue = "false", matchIfMissing = true)
public class AuthChain {

    public boolean auth(String userCode, String token, @NonNull HttpServletRequest request,
                        @NonNull HttpServletResponse response) throws IOException {
        boolean flag = false;
        if (userCode == null || token == null) {
            flag = true;
            CommonMethod.failed(request, response, DataEnums.USER_NOT_LOGIN);
        }
        boolean tokenExpired = JwtUtils.isTokenExpired(token);
        if (tokenExpired) {
            flag = true;
            CommonMethod.failed(request, response, DataEnums.TOKEN_LOGIN_EXPIRED);
        }
        boolean equals = Objects.equals(JwtUtils.getBodyFromToken(token), userCode);
        if (!equals) {
            flag = true;
            CommonMethod.failed(request, response, DataEnums.USER_NOT_LOGIN);
        }
        return flag;
    }
}
