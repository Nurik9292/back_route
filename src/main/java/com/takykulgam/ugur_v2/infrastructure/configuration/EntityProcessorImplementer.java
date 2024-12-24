package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.iteractor.geo.PointCreateUseCase;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.processors.GeoProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.CityEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StopEntity;
import com.takykulgam.ugur_v2.interfaces.processors.BannerEntityProcessor;
import com.takykulgam.ugur_v2.interfaces.processors.CityEntityProcessor;
import com.takykulgam.ugur_v2.interfaces.processors.StaffEntityProcessor;
import com.takykulgam.ugur_v2.interfaces.processors.StopEntityProcessor;
import org.locationtech.jts.geom.GeometryFactory;
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
    public GeoProcessor<StopEntity> stopEntityGeoProcessor(PointCreateUseCase pointCreateUseCase) {
        return new StopEntityProcessor(pointCreateUseCase);
    }
}

