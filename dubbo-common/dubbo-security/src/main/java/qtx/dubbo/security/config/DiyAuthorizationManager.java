package qtx.dubbo.security.config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * url权限校验
 *
 * @author qtx
 * @date 2022/9/6 23:16
 */
@Component
public class DiyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Collection<? extends GrantedAuthority> collection = authentication.get()
                .getAuthorities();
        String path = object.getRequest()
                .getServletPath();
        String login = "/report/sysUser/login";
        String createUser = "/report/sysUser/createUser";
        if (login.equals(path) || createUser.equals(path)) {
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(true);
//        return new AuthorizationDecision(roleUrlTask.getAuth(collection, path));
    }
}
