package com.takykulgam.ugur_v2;

import com.takykulgam.ugur_v2.applications.gateways.BusRepository;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.infrastructure.external.BusAtLogistikRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusImdataRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.interfaces.presenters.SimpleExecutor;
import com.takykulgam.ugur_v2.interfaces.processors.StaffEntityProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class UgurV2Application {

    public static void main(String[] args) {
        SpringApplication.run(UgurV2Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BusRepository busImdataRepository() {
        return new BusImdataRepository();
    }

    @Bean
    public BusRepository busAtLogistikRepository() {
        return new BusAtLogistikRepository();
    }

    @Bean
    public EntityProcessor<StaffEntity> staffEntityProcessor() {
        return new StaffEntityProcessor(passwordEncoder());
    }


}
