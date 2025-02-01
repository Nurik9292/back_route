package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.domain.gateways.*;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.*;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.*;
import com.takykulgam.ugur_v2.infrastructure.external.BusAtLogistikRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusImdataRepository;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystem;
import com.takykulgam.ugur_v2.interfaces.gateway.*;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityRouteDirectionMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityRouteMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityStaffMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityStopMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.*;
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
    public BannerRepository bannerRepository(R2dbcBannerRepository repository,
                                             EntityProcessor<BannerEntity> bannerEntityProcessor,
                                             EntityOutputBannerMapper bannerEntityOutputMapper) {
        return new BannerRepositoryImpl(repository, bannerEntityProcessor, bannerEntityOutputMapper);
    }

    @Bean
    public ImageRepository imageRepository(FileSystem fileSystem) {
        return new ImageRepositoryImpl(fileSystem);
    }

    @Bean
    public CityRepository cityRepository(R2dbcCityRepository repository,
                                         EntityProcessor<CityEntity> cityEntityProcessor,
                                         EntityOutputCityMapper cityEntityOutputMapper) {
        return new CityRepositoryImpl(repository, cityEntityProcessor, cityEntityOutputMapper);
    }

    @Bean
    public StaffRepository staffRepository(R2dbcStaffRepository repository,
                                           EntityProcessor<StaffEntity> staffEntityProcessor,
                                           EntityOutputStaffMapper staffEntityOutputMapper,
                                           DomainEntityStaffMapper domainEntityStaffMapper) {
        return new StaffRepositoryImpl(repository, staffEntityProcessor, staffEntityOutputMapper, domainEntityStaffMapper);
    }

    @Bean
    public StopRepository stopRepository(R2dbcStopRepository repository,
                                         EntityProcessor<StopEntity> stopEntityProcessor,
                                         DomainEntityStopMapper domainEntityStopMapper,
                                         EntityOutputStopMapper stopEntityOutputMapper) {
        return new StopRepositoryImpl(repository, stopEntityProcessor, domainEntityStopMapper, stopEntityOutputMapper);

    }

    @Bean
    public RouteRepository routeRepository(R2dbcRouteRepository routeRepository,
                                           DomainEntityRouteMapper domainEntityRouteMapper,
                                           EntityProcessor<RouteEntity> routeEntityProcessor,
                                           EntityOutputRouteMapper routeEntityOutputMapper) {
        return new RouteRepositoryImpl(routeRepository, domainEntityRouteMapper, routeEntityProcessor, routeEntityOutputMapper);
    }

    @Bean
    public RouteDirectionRepository routeDirectionRepository(R2dbcRouteDirectionRepository routeDirectionRepository,
                                                             EntityProcessor<RouteDirectionEntity> entityEntityProcessor,
                                                             DomainEntityRouteDirectionMapper domainMapper,
                                                             EntityOutputRouteDirMapper entityOutputMapper) {
        return new RouteDirectionRepositoryImpl(routeDirectionRepository, entityEntityProcessor, domainMapper, entityOutputMapper);
    }
}
