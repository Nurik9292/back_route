package com.takykulgam.ugur_v2.applications.usecase.route;

import com.takykulgam.ugur_v2.applications.boundaries.input.route.InputRouteCreate;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.entities.Route;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.RouteDirectionRepository;
import com.takykulgam.ugur_v2.domain.gateways.RouteRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;


@Log4j2
public class RouteCreateUseCase implements GenericUseCase<Mono<InputRouteCreate>, RouteCreateUseCase.Output> {

    private final RouteRepository routeRepository;
    private final RouteDirectionRepository routeDirectionRepository;

    public RouteCreateUseCase(RouteRepository routeRepository, RouteDirectionRepository routeDirectionRepository) {
        this.routeRepository = routeRepository;
        this.routeDirectionRepository = routeDirectionRepository;
    }

    @Override
    public Output execute(Mono<InputRouteCreate> request) {
        return new Output(request
                .map(InputRouteCreate::toEntity)
                .doOnNext(this::validationRoute)
                .flatMap(this::storeRouteWithDirections)
                .doOnSuccess(e -> log.info("Route successfully saved: {}", e))
                .doOnError(e -> log.error("Error saving route: ", e))
                .onErrorResume(e ->  Mono.error(new CoreException("Ошибка сохранения маршрута: %s".formatted(e.getMessage()))))
        );
    }

    private Mono<OutputRoute> storeRouteWithDirections(Route route) {
        return routeRepository.store(route)
                .flatMap(outputRoute -> Mono.when(
                    routeDirectionRepository.store(route.toRouteDirection(), outputRoute.id()),
                    routeDirectionRepository.store(route.fromRouteDirection(), outputRoute.id())
                    )
                    .thenReturn(outputRoute)
                );
    }

    private void validationRoute(Route route) {
        route.validationInterval();
        route.validationStops();
        route.validationNumber();
        route.validationDirections();
        route.validateCity();
    }


    public record Output(Mono<OutputRoute> result) {}


}
