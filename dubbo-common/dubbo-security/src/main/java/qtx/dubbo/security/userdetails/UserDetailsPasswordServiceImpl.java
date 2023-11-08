package qtx.dubbo.security.userdetails;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Component;

/**
 * @author qtx
 * @since 2023/9/23 22:45
 */
@Component
@ConditionalOnProperty(name = "spring.security", havingValue = "true")
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}
