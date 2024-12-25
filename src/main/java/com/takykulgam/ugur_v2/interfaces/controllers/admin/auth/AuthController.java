package com.takykulgam.ugur_v2.interfaces.controllers.admin.auth;

import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.applications.usecase.staff.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.interfaces.dto.staff.AuthStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping( value = "/admin/auth",  produces = "application/json")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthStaffLoginCase authStaffLoginCase;
    private final UseCaseExecutor executor;

    @Autowired
    public AuthController(AuthStaffLoginCase authStaffLoginCase, UseCaseExecutor executor) {
        this.authStaffLoginCase = authStaffLoginCase;
        this.executor = executor;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthStaff authStaff) {
        return executor.execute(
                authStaffLoginCase,
                authStaff.toInput(),
                AuthStaffLoginCase.Output::accessToken);
    }
}
