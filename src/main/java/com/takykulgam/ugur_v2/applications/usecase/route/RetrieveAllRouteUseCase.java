package com.takykulgam.ugur_v2.applications.usecase.route;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.RouteRepository;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.utils.PaginationUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class RetrieveAllRouteUseCase implements GenericUseCase<Mono<InputPaginate>, RetrieveAllRouteUseCase.Output> {

    private final RouteRepository routeRepository;

    public RetrieveAllRouteUseCase(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Output execute(Mono<InputPaginate> request) {
        return new Output(request
                .flatMap(input -> routeRepository
                        .findAll()
                        .collectList()
                        .flatMap(fullList -> Flux
                                .fromIterable(fullList)
                                .transform(routes -> PaginationUtils.sort(
                                        routes,
                                        createComparator(input.sort(), input.order()))
                                )
                                .transform(routes -> PaginationUtils.paginate(
                                        routes,
                                        input.page(),
                                        input.size())
                                )
                                .collectList()
                                .map(routeList -> PaginationUtils.createPage(
                                        routeList,
                                        fullList.size(),
                                        input.page(),
                                        input.size())
                                )
                        )
                )
        );
    }

    private Comparator<OutputRoute> createComparator(String sort, String order) {
        Comparator<OutputRoute> comparator = switch (sort.toLowerCase()) {
            case "name" -> Comparator.comparing(OutputRoute::name, String::compareToIgnoreCase);
            case "number" -> Comparator.comparingInt(OutputRoute::number);
            case "id" -> Comparator.comparingLong(OutputRoute::id);
            default -> throw new CoreException("Unsupported sort field: %s".formatted(sort));
        };

        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }


    public record Output(Mono<PageResult<OutputRoute>> result) {}
}
