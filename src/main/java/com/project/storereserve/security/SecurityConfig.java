package com.project.storereserve.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    private final UserDetailsService userDetailsService;
    // SecurityFilterChain 설정
    // Spring Security 의 보안설정을 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화, Rest API 에서는 보통 비활성화
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 상태 없는 세션 정책, JWT 는 세션이 필요하지 않음
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/login","/users/register","/stores/search", "/stores/{storeId}").permitAll() // "/auth/login" 은 인증 없이 접근 가능
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터를 UsernamePasswordAuthenticationFilter 전에 추가

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 암호화 필요 시 추가

        return new ProviderManager(authProvider);
    }
    // 회원가입시 비밀번호
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
