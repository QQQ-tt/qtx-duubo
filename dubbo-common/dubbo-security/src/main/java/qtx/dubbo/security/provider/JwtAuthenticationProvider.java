package qtx.dubbo.security.provider;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import qtx.dubbo.config.utils.JwtUtils;

import java.util.List;

/**
 * @author qtx
 * @since 2023/9/25
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = authentication.getCredentials()
                .toString();
        String body = JwtUtils.getBodyFromToken(jwt);
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
