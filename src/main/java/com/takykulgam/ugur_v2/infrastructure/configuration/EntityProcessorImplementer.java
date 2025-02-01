package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.usecase.geo.PointCreateUseCase;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.processors.PointProcessor;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.*;
import com.takykulgam.ugur_v2.interfaces.processors.*;
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

    @Bean
    public EntityProcessor<StopEntity> stopEntityEntityProcessor(PointCreateUseCase pointCreateUseCase) {
        return new StopEntityProcessor(pointCreateUseCase);
    }

    @Bean
    public PointProcessor<StopEntity> stopEntityGeoProcessor(PointCreateUseCase pointCreateUseCase) {
        return new StopEntityProcessor(pointCreateUseCase);
    }

    @Bean
    public EntityProcessor<RouteEntity> routeEntityProcessor() {
        return new RouteEntityProcessor();
    }

    @Bean
    public EntityProcessor<RouteDirectionEntity> routeDirectionEntityProcessor() {
        return new RouteDirectionEntityProcessor();
    }
}

