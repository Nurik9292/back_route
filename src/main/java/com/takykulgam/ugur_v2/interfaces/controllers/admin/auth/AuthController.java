package com.takykulgam.ugur_v2.interfaces.controllers.admin.auth;

import com.takykulgam.ugur_v2.applications.iteractor.staff.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.applications.dto.AuthStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping( value = "/admin/auth",  produces = "application/json")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final GenericUseCase<Mono<AuthStaffLoginCase.Input>, AuthStaffLoginCase.Output> authStaffLoginCase;
    private final UseCaseExecutor executor;

    @Autowired
    public AuthController(GenericUseCase<Mono<AuthStaffLoginCase.Input>, AuthStaffLoginCase.Output> authStaffLoginCase, UseCaseExecutor executor) {
        this.authStaffLoginCase = authStaffLoginCase;
        this.executor = executor;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthStaff authStaff) {
        return executor.execute(
                authStaffLoginCase,
                Mono.just(authStaff).map(AuthStaff::toInput),
                AuthStaffLoginCase.Output::accessToken);
    }
}
