package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.infrastructure.security.admin.AdminAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AdminAuthenticationFilter adminAuthenticationFilter;

    @Autowired
    public SecurityConfig(AdminAuthenticationFilter adminAuthenticationFilter) {
        this.adminAuthenticationFilter = adminAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .securityMatcher("/admin/**")
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("admin/auth/**").permitAll()
                            .requestMatchers("admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated())
                    .addFilterBefore(adminAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .formLogin(AbstractHttpConfigurer::disable)
                    .build();
    }


}
