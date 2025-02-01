package com.takykulgam.ugur_v2.interfaces.mappers.domainEntity;

import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.entities.Route;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteEntity;

public class DomainEntityRouteMapper {


    public DomainEntityRouteMapper() {
    }

    public RouteEntity toEntity(Domain domain) {
        Route route = (Route) domain;
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setName(route.name());
        routeEntity.setNumber(route.number());
        routeEntity.setInterval(route.interval());
        routeEntity.setCityId(route.cityId());
        return routeEntity;
    }

}
