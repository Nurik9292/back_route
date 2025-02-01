package com.takykulgam.ugur_v2.domain.gateways;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRouteDirection;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import reactor.core.publisher.Mono;

public interface RouteDirectionRepository {

    public Mono<OutputRouteDirection> store(Domain domain, Long routeId);
}
