package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.CityEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.interfaces.processors.BannerEntityProcessor;
import com.takykulgam.ugur_v2.interfaces.processors.CityEntityProcessor;
import com.takykulgam.ugur_v2.interfaces.processors.StaffEntityProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EntityProcessorImplementer {


    @Bean
    public EntityProcessor<StaffEntity> staffEntityProcessor(PasswordEncoder passwordEncoder) {
        return new StaffEntityProcessor(passwordEncoder);
    }

    @Bean
    public EntityProcessor<CityEntity> cityEntityProcessor() {
        return new CityEntityProcessor();
    }

    @Bean
    public EntityProcessor<BannerEntity> bannerEntityProcessor() {
        return new BannerEntityProcessor();
    }
}
