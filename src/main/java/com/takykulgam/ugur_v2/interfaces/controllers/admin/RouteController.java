package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.applications.usecase.route.RetrieveAllRouteUseCase;
import com.takykulgam.ugur_v2.applications.usecase.route.RouteCreateUseCase;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.interfaces.dto.route.CreateRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/routes")
public class RouteController {

    private final RetrieveAllRouteUseCase retrieveAllRouteUseCase;
    private final RouteCreateUseCase routeCreateUseCase;
    private final UseCaseExecutor useCaseExecutor;

    @Autowired
    public RouteController(RetrieveAllRouteUseCase retrieveAllRouteUseCase,
                           RouteCreateUseCase routeCreateUseCase,
                           UseCaseExecutor useCaseExecutor) {
        this.retrieveAllRouteUseCase = retrieveAllRouteUseCase;
        this.routeCreateUseCase = routeCreateUseCase;
        this.useCaseExecutor = useCaseExecutor;
    }

    @GetMapping
    public Mono<PageResult<OutputRoute>> getAllRoutes(@RequestParam(name = "order") String order,
                                                     @RequestParam(name = "sort") String sort,
                                                     @RequestParam(name = "page") int page,
                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        System.out.println(order);
        System.out.println(sort);
        System.out.println(page);
        System.out.println(size);
        return useCaseExecutor.execute(
                retrieveAllRouteUseCase,
                Mono.just(new InputPaginate(page, size, sort, order)),
                RetrieveAllRouteUseCase.Output::result
        );
    }

    @PostMapping
    public Mono<OutputRoute> addRoute(@RequestBody CreateRoute createRoute) {
        System.out.println(createRoute.toString());
        return useCaseExecutor.execute(
                routeCreateUseCase,
                createRoute.toInput(),
                RouteCreateUseCase.Output::result
        );
    }
}
