package qtx.dubbo.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import qtx.dubbo.security.filter.JwtAuthTokenFilter;
import qtx.dubbo.security.provider.JwtAuthenticationProvider;
import qtx.dubbo.security.userdetails.UserDetailsServiceImpl;

/**
 * @author qtx
 * @since 2023/9/23 21:55
 */
@Configuration
@EnableMethodSecurity
@ConditionalOnProperty(name = "spring.security", havingValue = "true")
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final DiyAccessDeniedHandler accessDeniedHandler;

    private final DiyAuthenticationEntryPoint authenticationEntryPoint;

    private final DiyAuthorizationManager authorizationManager;

    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, DiyAccessDeniedHandler accessDeniedHandler,
                          DiyAuthenticationEntryPoint authenticationEntryPoint,
                          DiyAuthorizationManager authorizationManager, JwtAuthTokenFilter jwtAuthTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authorizationManager = authorizationManager;
        this.jwtAuthTokenFilter = jwtAuthTokenFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeHttpRequests(auth -> auth.anyRequest()
                        .access(authorizationManager))
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(new JwtAuthenticationProvider())
                .cors();
        return http.build();
    }

    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
