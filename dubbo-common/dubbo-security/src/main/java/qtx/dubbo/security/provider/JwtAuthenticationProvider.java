package qtx.dubbo.security.provider;

import io.jsonwebtoken.Claims;
import org.apache.dubbo.common.utils.StringUtils;
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
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.security.util.JwtUtils;

import java.util.List;
import java.util.Map;

/**
 * @author qtx
 * @since 2023/9/25
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final RedisUtils redisUtils;

    private final CommonMethod commonMethod;

    public JwtAuthenticationProvider(RedisUtils redisUtils, CommonMethod commonMethod) {
        this.redisUtils = redisUtils;
        this.commonMethod = commonMethod;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = authentication.getCredentials()
                .toString();
        String userCode = commonMethod.getUserCode();
        if (StringUtils.isBlank(commonMethod.getToken())){
            throw new UsernameNotFoundException(DataEnums.USER_NOT_LOGIN.getMsg());
        }
        Map<Object, Object> redisUser = redisUtils.getHashMsg(
                StaticConstant.LOGIN_USER + userCode + StaticConstant.REDIS_INFO);
        if (redisUser == null || redisUser.isEmpty()) {
            throw new UsernameNotFoundException(DataEnums.USER_NOT_LOGIN.getMsg());
        }

        String body = JwtUtils.getBodyFromToken(jwt);

        if (!redisUser.get("userCode")
                .toString()
                .equals(body)) {
            throw new BadCredentialsException(DataEnums.TOKEN_IS_ILLEGAL.getMsg());
        }
        Claims claims = JwtUtils.getClaimsFromToken(jwt);
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
