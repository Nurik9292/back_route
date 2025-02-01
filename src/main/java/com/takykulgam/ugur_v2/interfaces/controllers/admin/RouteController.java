package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputRoute;
import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.applications.usecase.route.RouteCreateUseCase;
import com.takykulgam.ugur_v2.infrastructure.configuration.UseCaseImplementer;
import com.takykulgam.ugur_v2.interfaces.dto.route.CreateRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/routes")
public class RouteController {

    private final RouteCreateUseCase routeCreateUseCase;
    private final UseCaseExecutor useCaseExecutor;

    @Autowired
    public RouteController(RouteCreateUseCase routeCreateUseCase,UseCaseExecutor useCaseExecutor) {
        this.routeCreateUseCase = routeCreateUseCase;
        this.useCaseExecutor = useCaseExecutor;
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
