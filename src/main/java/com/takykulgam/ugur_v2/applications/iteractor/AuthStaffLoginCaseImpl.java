package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.boundaries.dto.AuthStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.auth.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;
import reactor.core.publisher.Mono;

public class AuthStaffLoginCaseImpl implements AuthStaffLoginCase {

    private final StaffRepository staffRepository;
    private final CustomerPasswordEncoder passwordEncoder;
    private final CustomAuthentication customAuthentication;
    private final Presenter<String, Mono<Response<StaffAuthViewModel>>> presenter;

    public AuthStaffLoginCaseImpl(StaffRepository staffRepository,
                                  CustomerPasswordEncoder passwordEncoder,
                                  CustomAuthentication customAuthentication,
                                  Presenter<String, Mono<Response<StaffAuthViewModel>>> presenter) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.customAuthentication = customAuthentication;
        this.presenter = presenter;

    }

    @Override
    public Mono<Void> login(AuthStaff staff) {
        return staffRepository.findByName(staff.getName())
                .flatMap(existStaff -> {
                    return staffRepository.passwordHash(existStaff.getId())
                            .flatMap(storedPasswordHash -> {
                                if (passwordEncoder.matches(staff.getPassword(), storedPasswordHash)) {
                                    return customAuthentication.authenticate(existStaff.getName(), staff.getPassword())
                                            .flatMap(authResult -> presenter.present(true, authResult));
                                } else {
                                    return presenter.present(false, "Wrong password");
                                }
                            });
                })
                .onErrorResume(e -> presenter.present(false, "An error occurred: " + e.getMessage()))
                .then();
    }
}
