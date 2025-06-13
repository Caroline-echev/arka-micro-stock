package com.arka.micro_stock.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()

                        .pathMatchers(HttpMethod.GET, "/api/countries/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/providers/**").permitAll()

                        .pathMatchers(HttpMethod.POST, "/api/countries/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/countries/**").hasRole("ADMIN")

                        .pathMatchers(HttpMethod.POST, "/api/providers/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/providers/**").hasRole("ADMIN")

                        .anyExchange().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
