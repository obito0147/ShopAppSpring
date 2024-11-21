package com.example.shoapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.shoapp.models.User;
import com.example.shoapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        // tra ve la 1 truong duy nhat cua user -> tai khoan dang nhap -> so dien thoai
        return phoneNumber -> {
            User existingUser = userRepository
                    .findByPhoneNumber(phoneNumber)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("Cannot find user with phone number = " + phoneNumber));
            return existingUser;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // trong hàm này bao gồm cả hàm encode() và matches()
        return new BCryptPasswordEncoder();
    }

    // Cung cấp logic xác thực chi tiết (lấy thông tin người dùng và kiểm tra mật
    // khẩu).
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Là trung gian chịu trách nhiệm quản lý và phối hợp các AuthenticationProvider
    // để xác thực người dùng.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
