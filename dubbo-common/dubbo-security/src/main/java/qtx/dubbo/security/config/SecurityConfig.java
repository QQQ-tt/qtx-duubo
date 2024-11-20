package qtx.dubbo.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import qtx.dubbo.redis.util.RedisUtils;
import qtx.dubbo.security.filter.SecurityAuthFilter;
import qtx.dubbo.security.provider.JwtAuthenticationProvider;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author qtx
 * @since 2023/9/23 21:55
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@ConditionalOnProperty(name = "spring.security", havingValue = "true")
public class SecurityConfig {

    private final DiyAccessDeniedHandler accessDeniedHandler;

    private final DiyAuthenticationEntryPoint authenticationEntryPoint;

    private final DiyAuthorizationManager authorizationManager;

    private final RedisUtils redisUtils;

    public SecurityConfig(DiyAccessDeniedHandler accessDeniedHandler,
                          DiyAuthenticationEntryPoint authenticationEntryPoint,
                          DiyAuthorizationManager authorizationManager, RedisUtils redisUtils) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authorizationManager = authorizationManager;
        this.redisUtils = redisUtils;
    }

    /**
     * 密码解密方式
     *
     * @return 密码加密对象
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/user/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth.anyRequest()
                        .permitAll());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain myFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .access(authorizationManager)).
                authenticationProvider(new JwtAuthenticationProvider(redisUtils));
        SecurityAuthFilter filter = new SecurityAuthFilter();
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

        SecurityFilterChain chain = http.build();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        return chain;
    }

    /**
     * 跨域配置
     */
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:10087", "http://192.168.3.124:10087"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);

        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
