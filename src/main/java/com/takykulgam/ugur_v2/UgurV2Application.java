package com.takykulgam.ugur_v2;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.interfaces.processors.StaffEntityProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class UgurV2Application {

    public static void main(String[] args) {
        SpringApplication.run(UgurV2Application.class, args);
    }


    @Bean
    public EntityProcessor<StaffEntity> staffEntityProcessor(PasswordEncoder passwordEncoder) {
        return new StaffEntityProcessor(passwordEncoder);
    }


}
