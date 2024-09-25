package uz.pdp.app_codingbat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.app_codingbat.config.filter.LogFilter;
import uz.pdp.app_codingbat.config.handler.JwtAuthenticationEntryPoint;
import uz.pdp.app_codingbat.config.handler.MyAccessDeniedHandler;
import uz.pdp.app_codingbat.config.jwt.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final MyAccessDeniedHandler myAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogFilter logFilter;

    public SecurityConfig(
            @Lazy JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            @Lazy MyAccessDeniedHandler myAccessDeniedHandler,
            @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
            @Lazy LogFilter logFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logFilter = logFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().permitAll()
                )
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    handling.accessDeniedHandler(myAccessDeniedHandler);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(logFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
