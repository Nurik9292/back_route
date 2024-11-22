package com.takykulgam.ugur_v2.interfaces.controllers.admin.auth;

import com.takykulgam.ugur_v2.core.boundaries.dto.AuthStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.auth.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
public class AuthController {

    private final AuthStaffLoginCase authStaffLoginCase;
    private final Presenter<String, Response<StaffAuthViewModel>> presenter;

    @Autowired
    public AuthController(AuthStaffLoginCase authStaffLoginCase, Presenter<String, Response<StaffAuthViewModel>> presenter) {
        this.authStaffLoginCase = authStaffLoginCase;
        this.presenter = presenter;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<StaffAuthViewModel>> login(@RequestBody AuthStaff request) {
        System.out.println(request);
        authStaffLoginCase.login(request);
        return ResponseEntity.ok(presenter.getResponse());
    }
}
