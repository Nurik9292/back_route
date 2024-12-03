package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.security.admin.StaffDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/home")
public class HomeController {


    @GetMapping
    public Mono<StaffEntity> home() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    StaffDetails staffDetails = (StaffDetails) authentication.getPrincipal();
                    return staffDetails.getStaffEntity();
                })
                .switchIfEmpty(Mono.error(new SecurityException("User not authenticated")));
    }
}
