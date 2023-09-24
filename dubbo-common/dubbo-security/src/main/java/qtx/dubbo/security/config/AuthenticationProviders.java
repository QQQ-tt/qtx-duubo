package qtx.dubbo.security.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import qtx.dubbo.security.userdetails.UserDetailsServiceImpl;

/**
 * @author qtx
 * @since 2023/9/23 23:16
 */
@Configurable
public class AuthenticationProviders {

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsServiceImpl userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
