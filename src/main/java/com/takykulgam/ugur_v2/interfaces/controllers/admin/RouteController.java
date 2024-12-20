package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.interfaces.dto.route.CreateRoute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/routes")
public class RouteController {


    @PostMapping
    public Mono<Void> addRoute(@RequestBody CreateRoute createRoute) {
        System.out.println(createRoute.toString());
        return Mono.empty();
    }
}
