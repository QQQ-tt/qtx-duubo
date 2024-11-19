package qtx.dubbo.security.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.java.util.JwtUtils;
import qtx.dubbo.security.Login;


/**
 * @author qtx
 * @since 2023/9/23 23:02
 */
public class SecurityAuthFilter extends AbstractAuthFilter {

    public SecurityAuthFilter() {
        super(new AntPathRequestMatcher("/login/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        CommonMethod.setUri(request.getRequestURI());
        CommonMethod.setIp(request.getRemoteAddr());
        String userCode = "";
        String token;
        if (requiresAuthenticationRequestMatcher.matches(request)) {
            Assert.notNull(super.login, "login info is null");
            Login login = JSON.parseObject(super.login, Login.class);
            userCode = login.getUserCode();
            token = login.getPassword();
        } else {
            token = request.getHeader(StaticConstant.TOKEN);
            CommonMethod.setToken(token);
            if (StringUtils.isBlank(token)) {
                throw new BadCredentialsException(DataEnums.ACCESS_DENIED.getMsg() + request.getMethod());
            }
            token = token.split(" ")[1];
            if (!JwtUtils.validateToken(token)) {
                throw new BadCredentialsException(DataEnums.TOKEN_IS_ILLEGAL.getMsg() + request.getMethod());
            }
            if (JwtUtils.isTokenExpired(token)) {
                throw new BadCredentialsException(DataEnums.TOKEN_LOGIN_EXPIRED.getMsg() + request.getMethod());
            }
        }
        CommonMethod.setUserCode(userCode);
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(userCode,
                token);
        return this.getAuthenticationManager()
                .authenticate(authRequest);
    }
}
