package com.takykulgam.ugur_v2.interfaces.controllers.admin.auth;

import com.takykulgam.ugur_v2.core.boundaries.dto.AuthStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.auth.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping( value = "/admin/auth",  produces = "application/json")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthStaffLoginCase authStaffLoginCase;
    private final Presenter<String, Mono<Response<StaffAuthViewModel>>> presenter;

    @Autowired
    public AuthController(AuthStaffLoginCase authStaffLoginCase,
                          Presenter<String, Mono<Response<StaffAuthViewModel>>> presenter) {
        this.authStaffLoginCase = authStaffLoginCase;
        this.presenter = presenter;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Response<StaffAuthViewModel>>> login(@RequestBody AuthStaff authStaff) {
        System.out.println(authStaff);
        return authStaffLoginCase.login(authStaff)
                .then(Mono.defer(presenter::getResponse))
                .map(ResponseEntity::ok);
    }
}
