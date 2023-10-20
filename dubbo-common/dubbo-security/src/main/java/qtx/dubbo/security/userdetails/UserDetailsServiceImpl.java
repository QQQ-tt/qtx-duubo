package qtx.dubbo.security.userdetails;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.redis.util.RedisUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author qtx
 * @since 2023/9/23 22:26
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RedisUtils redisUtils;

    public UserDetailsServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<Object, Object> hashMsg = redisUtils.getHashMsg(
                StaticConstant.SYS_USER + username + StaticConstant.REDIS_INFO);
        if (Objects.isNull(hashMsg) || hashMsg.isEmpty()) {
            throw new UsernameNotFoundException("用户为空");
        }
        return new User(String.valueOf(hashMsg.get("user_code")), String.valueOf(hashMsg.get("password")),
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(hashMsg.get("user_role"))));
    }
}
