package com.takykulgam.ugur_v2.applications.usecase.route;

import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.RouteRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public class RouteCreateUseCase implements GenericUseCase<Mono<RouteCreateUseCase.Input>, RouteCreateUseCase.Output> {

    private final RouteRepository routeRepository;

    public RouteCreateUseCase(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return null;
    }


    public record Input(String name, int number, String description, List<Point> path, City city) {}

    public record Output(Mono<Output> result) {}

    public record Point(double lat, double lng) {}

    public record City(long cityId) {}
}
