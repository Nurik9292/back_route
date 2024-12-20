package com.takykulgam.ugur_v2.applications.iteractor.route;

import com.takykulgam.ugur_v2.core.boundaries.output.OutputRoute;
import reactor.core.publisher.Mono;

import java.util.List;

public class RetrieveAllRouteUseCase {



    public record Input(String name, String dir, List<Point> path) {}

    public record Output(Mono<OutputRoute> result) {}

    public record Point(double x, double y) {}
}
