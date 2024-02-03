package com.ilabs.authservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ilabs.authservice.entity.Permission.*;
import static com.ilabs.authservice.entity.Role.ADMIN;
import static com.ilabs.authservice.entity.Role.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()

                        //USER ENDPOINTS
                        .requestMatchers("/api/v1/cart/**").hasRole(USER.name())
                        .requestMatchers(GET, "/api/v1/cart/**").hasAnyAuthority(USER_READ.name())
                        .requestMatchers(POST, "/api/v1/cart/**").hasAnyAuthority(USER_READ.name())
                        .requestMatchers(DELETE, "/api/v1/cart/**").hasAnyAuthority(USER_DELETE.name())

                        //ADMIN ENDPOINTS
                        .requestMatchers("/api/v1/item/**").hasRole(ADMIN.name())
                        .requestMatchers(GET, "/api/v1/item/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/item/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/item/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/item/**").hasAuthority(ADMIN_DELETE.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
