package qtx.dubbo.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Component;

/**
 * @author qtx
 * @since 2023/9/23 22:45
 */
@Component
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}
