package qtx.dubbo.security.provider;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import qtx.dubbo.java.CommonMethod;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.java.util.JwtUtils;
import qtx.dubbo.redis.util.RedisUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author qtx
 * @since 2023/9/25
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final RedisUtils redisUtils;

    public JwtAuthenticationProvider(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object authenticationCredentials = authentication.getCredentials();
        if (Objects.isNull(authenticationCredentials)) {
            throw new BadCredentialsException(DataEnums.ACCESS_DENIED.getMsg());
        }
        String token;
        try {
            token = authenticationCredentials.toString()
                    .split(" ")[1];
        } catch (Exception e) {
            throw new BadCredentialsException(DataEnums.TOKEN_IS_NULL.getMsg());
        }
        CommonMethod.setToken(token);
        if (!JwtUtils.validateToken(token)) {
            throw new BadCredentialsException(DataEnums.TOKEN_IS_ILLEGAL.getMsg());
        }
        if (JwtUtils.isTokenExpired(token)) {
            throw new BadCredentialsException(DataEnums.TOKEN_LOGIN_EXPIRED.getMsg());
        }
        String body;
        try {
            body = JwtUtils.getBodyFromToken(token);
        } catch (Exception e) {
            throw new BadCredentialsException(DataEnums.TOKEN_IS_ILLEGAL.getMsg());
        }

        Map<Object, Object> redisUser = redisUtils.getHashMsg(
                StaticConstant.LOGIN_USER + body + StaticConstant.REDIS_INFO);
        if (redisUser == null || redisUser.isEmpty()) {
            throw new UsernameNotFoundException(DataEnums.USER_NOT_LOGIN.getMsg());
        }

        Claims claims = JwtUtils.getClaimsFromToken(token);
        assert claims != null;
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                String.valueOf(claims.get("roles")));
        UserDetails userDetails = new User(body, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
