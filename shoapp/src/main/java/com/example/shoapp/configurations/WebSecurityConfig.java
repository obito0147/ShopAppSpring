package com.example.shoapp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.shoapp.filters.JwtTokenFilter;
import com.example.shoapp.models.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
        private final JwtTokenFilter jwtTokenFilter;
        @Value("${api.prefix}")
        private String apiPrefix;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(request -> {
                                        request
                                                        .requestMatchers(
                                                                        String.format("%s/users/register", apiPrefix),
                                                                        String.format("%s/users/login", apiPrefix))
                                                        .permitAll()
                                                        // CATEGORY
                                                        .requestMatchers(HttpMethod.GET,
                                                                        String.format("%s/categories**", apiPrefix))
                                                        .hasAnyRole(Role.USER, Role.ADMIN)
                                                        .requestMatchers(HttpMethod.POST,
                                                                        String.format("%s/categories/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        .requestMatchers(HttpMethod.PUT,
                                                                        String.format("%s/categories/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        .requestMatchers(HttpMethod.DELETE,
                                                                        String.format("%s/categories/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        // PRODUCT
                                                        .requestMatchers(HttpMethod.GET,
                                                                        String.format("%s/products**", apiPrefix))
                                                        .hasAnyRole(Role.USER, Role.ADMIN)
                                                        .requestMatchers(HttpMethod.POST,
                                                                        String.format("%s/products/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        .requestMatchers(HttpMethod.PUT,
                                                                        String.format("%s/products/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        .requestMatchers(HttpMethod.DELETE,
                                                                        String.format("%s/products/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        // ORDER
                                                        .requestMatchers(HttpMethod.POST,
                                                                        String.format("%s/orders/**", apiPrefix))
                                                        .hasRole(Role.USER)
                                                        .requestMatchers(HttpMethod.GET,
                                                                        String.format("%s/orders/**", apiPrefix))
                                                        .hasAnyRole(Role.USER, Role.ADMIN)
                                                        .requestMatchers(HttpMethod.PUT,
                                                                        String.format("%s/orders/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        .requestMatchers(HttpMethod.DELETE,
                                                                        String.format("%s/orders/**", apiPrefix))
                                                        .hasRole(Role.ADMIN)
                                                        // ORDER DETAIL
                                                        .requestMatchers(HttpMethod.GET,
                                                                        String.format("%s/order_details/**", apiPrefix))
                                                        .hasAnyRole(Role.USER)
                                                        .requestMatchers(HttpMethod.POST,
                                                                        String.format("%s/order_details/**", apiPrefix))
                                                        .hasRole(Role.USER)
                                                        .requestMatchers(HttpMethod.PUT,
                                                                        String.format("%s/order_details/**", apiPrefix))
                                                        .hasRole(Role.USER)
                                                        .requestMatchers(HttpMethod.DELETE,
                                                                        String.format("%s/order_details/**", apiPrefix))
                                                        .hasRole(Role.USER)
                                                        .anyRequest().authenticated();
                                });

                return http.build();
        }
}
