package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityRouteDirectionMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityRouteMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityStaffMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityStopMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.*;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperImplementer {

    @Bean
    public DomainEntityStopMapper domainEntityStopMapper(GeometryFactory geometryFactory) {
        return new DomainEntityStopMapper(geometryFactory);
    }

    @Bean
    public DomainEntityStaffMapper domainEntityStaffMapper() {
        return new DomainEntityStaffMapper();
    }

    @Bean
    public EntityOutputStaffMapper entityOutputStaffMapper() {
        return new EntityOutputStaffMapper();
    }

    @Bean
    public EntityOutputStopMapper entityOutputStopMapper() {
        return new EntityOutputStopMapper();
    }

    @Bean
    public EntityOutputCityMapper entityOutputCityMapper() {
        return new EntityOutputCityMapper();
    }

    @Bean
    public EntityOutputBannerMapper entityOutputBannerMapper() {
        return new EntityOutputBannerMapper();
    }

    @Bean
    public DomainEntityRouteMapper domainEntityRouteMapper() {
        return new DomainEntityRouteMapper();
    }

    @Bean
    public DomainEntityRouteDirectionMapper domainEntityRouteDirectionMapper(GeometryFactory geometryFactory) {
        return new DomainEntityRouteDirectionMapper(geometryFactory);
    }

    @Bean
    public EntityOutputRouteMapper entityOutputRouteMapper() {
        return new EntityOutputRouteMapper();
    }

    @Bean
    public EntityOutputRouteDirMapper entityOutputRouteDirMapper() {
        return new EntityOutputRouteDirMapper();
    }
}
