package qtx.dubbo.security.userdetails;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.redis.RedisUtils;

import java.util.Map;

/**
 * @author qtx
 * @since 2023/9/23 22:26
 */
@Component
@ConditionalOnProperty(name = "spring.security", havingValue = "true")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<Object, Object> hashMsg = RedisUtils.hGetAll(
                StaticConstant.SYS_USER + username + StaticConstant.REDIS_INFO);
        if (hashMsg.isEmpty()) {
            throw new UsernameNotFoundException("用户为空");
        }
        return new User(String.valueOf(hashMsg.get("userCode")), String.valueOf(hashMsg.get("password")),
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(hashMsg.get("user_role"))));
    }
}
