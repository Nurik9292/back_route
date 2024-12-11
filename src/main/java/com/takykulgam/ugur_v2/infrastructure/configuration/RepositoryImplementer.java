package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.core.domain.gateways.*;
import com.takykulgam.ugur_v2.infrastructure.external.BusAtLogistikRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusImdataRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.BannerEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.CityEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcBannerRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcCityRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystem;
import com.takykulgam.ugur_v2.interfaces.gateway.BannerRepositoryImpl;
import com.takykulgam.ugur_v2.interfaces.gateway.CityRepositoryImpl;
import com.takykulgam.ugur_v2.interfaces.gateway.ImageRepositoryImpl;
import com.takykulgam.ugur_v2.interfaces.gateway.StaffRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryImplementer {

    @Bean
    public BusRepository busImdataRepository() {
        return new BusImdataRepository();
    }

    @Bean
    public BusRepository busAtLogistikRepository() {
        return new BusAtLogistikRepository();
    }

    @Bean
    public BannerRepository bannerRepository(R2dbcBannerRepository repository, EntityProcessor<BannerEntity> bannerEntityProcessor) {
        return new BannerRepositoryImpl(repository, bannerEntityProcessor);
    }

    @Bean
    public ImageRepository imageRepository(FileSystem fileSystem) {
        return new ImageRepositoryImpl(fileSystem);
    }

    @Bean
    public CityRepository cityRepository(R2dbcCityRepository repository, EntityProcessor<CityEntity> cityEntityProcessor) {
        return new CityRepositoryImpl(repository, cityEntityProcessor);
    }

    @Bean
    public StaffRepository staffRepository(R2dbcStaffRepository repository, EntityProcessor<StaffEntity> staffEntityProcessor) {
        return new StaffRepositoryImpl(repository, staffEntityProcessor);
    }
}
