package com.takykulgam.ugur_v2.applications.iteractor.geo;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import reactor.core.publisher.Mono;

public class PointCreateUseCase implements GenericUseCase<Mono<PointCreateUseCase.Input>, PointCreateUseCase.Output> {

    private final GeometryFactory geometryFactory;

    public PointCreateUseCase(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request.flatMap(this::createPoint));
    }

    private Mono<Point> createPoint(Input input) {
        return Mono.just(geometryFactory.createPoint(new Coordinate(input.x(), input.y())));
    }


    public record Input(double x, double y) {
    }

    public record Output(Mono<Point> result) {}
}
