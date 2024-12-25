package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputAuth;
import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import reactor.core.publisher.Mono;

public class AuthStaffLoginCase implements GenericUseCase<Mono<InputAuth>, AuthStaffLoginCase.Output> {

    private final StaffGetByNameUseCase genericUseCase;
    private final CustomerPasswordEncoder passwordEncoder;
    private final CustomAuthentication customAuthentication;

    public AuthStaffLoginCase(StaffGetByNameUseCase genericUseCase,
                              CustomerPasswordEncoder passwordEncoder,
                              CustomAuthentication customAuthentication) {
        this.genericUseCase = genericUseCase;
        this.passwordEncoder = passwordEncoder;
        this.customAuthentication = customAuthentication;

    }


    @Override
    public Output execute(Mono<InputAuth> request) {
        Mono<String> token = request.flatMap(input ->
                genericUseCase.execute(Mono.just(input.username()))
                        .result()
                        .flatMap(outputStaff -> {
                            if (!passwordEncoder.matches(input.password(), outputStaff.getPassword()))
                                return Mono.error(new CoreException("Wrong password"));

                            return customAuthentication.authenticate(outputStaff.getName(), outputStaff.getPassword());
                        })
        );

        return new Output(token);
    }

    public record Output(Mono<String> accessToken) {}
}
