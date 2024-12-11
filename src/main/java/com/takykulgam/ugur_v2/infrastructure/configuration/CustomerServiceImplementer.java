package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.infrastructure.security.PasswordEncoderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomerServiceImplementer {

    @Bean
    public CustomerPasswordEncoder customerPasswordEncoder(PasswordEncoder passwordEncoder) {
        return new PasswordEncoderImpl(passwordEncoder);
    }
}
