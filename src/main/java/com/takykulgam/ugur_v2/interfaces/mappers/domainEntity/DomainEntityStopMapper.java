package com.takykulgam.ugur_v2.interfaces.mappers.domainEntity;

import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.entities.Stop;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StopEntity;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class DomainEntityStopMapper {

    private final GeometryFactory geometryFactory;

    public DomainEntityStopMapper(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

    public  StopEntity toEntity(Domain domain) {
        Stop stop = (Stop) domain;
        return new StopEntity(stop.getTitle(), stop.getCityId(), toPoint(stop));
    }

    private Point toPoint(Stop stop) {
        return geometryFactory.createPoint(new Coordinate(stop.getLocation().x(), stop.getLocation().y()));
    }
}
