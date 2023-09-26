package qtx.dubbo.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import qtx.dubbo.config.utils.CommonMethod;
import qtx.dubbo.config.utils.JwtUtils;
import qtx.dubbo.config.utils.RedisUtils;
import qtx.dubbo.java.enums.AuthUrlEnums;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.info.StaticConstant;

import java.io.IOException;
import java.util.Map;

/**
 * @author qtx
 * @since 2023/9/23 23:02
 */
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final CommonMethod commonMethod;

    private final RedisUtils redisUtils;

    public JwtAuthTokenFilter(CommonMethod commonMethod, RedisUtils redisUtils) {
        this.commonMethod = commonMethod;
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        commonMethod.setUri(uri);
        if (AuthUrlEnums.authPath(uri)) {
            filterChain.doFilter(request, response);
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

        String userCode = JwtUtils.getBodyFromToken(token);
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

        commonMethod.setUserCode(userCode);
        String ip = request.getRemoteAddr();
        commonMethod.setIp(ip);
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
                userCode, token);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(unauthenticated);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
