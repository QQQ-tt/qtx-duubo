package qtx.dubbo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * @since 2022/9/6 23:16
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.security", havingValue = "true")
public class DiyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        log.info("权限校验");
        Collection<? extends GrantedAuthority> collection = authentication.get()
                .getAuthorities();
        String path = object.getRequest()
                .getServletPath();
        // todo 校验待办
        return new AuthorizationDecision(true);
//        return new AuthorizationDecision(roleUrlTask.getAuth(collection, path));
    }
}
