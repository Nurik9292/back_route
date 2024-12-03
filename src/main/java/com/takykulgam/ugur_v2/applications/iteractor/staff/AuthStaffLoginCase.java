package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import reactor.core.publisher.Mono;

public class AuthStaffLoginCase implements GenericUseCase<Mono<AuthStaffLoginCase.Input>, AuthStaffLoginCase.Output> {

    private final GetStaffByNameUseCase genericUseCase;
    private final CustomerPasswordEncoder passwordEncoder;
    private final CustomAuthentication customAuthentication;

    public AuthStaffLoginCase(GetStaffByNameUseCase genericUseCase,
                              CustomerPasswordEncoder passwordEncoder,
                              CustomAuthentication customAuthentication) {
        this.genericUseCase = genericUseCase;
        this.passwordEncoder = passwordEncoder;
        this.customAuthentication = customAuthentication;

    }


    @Override
    public Output execute(Mono<Input> request) {
        Mono<String> token = request.flatMap(input ->
                genericUseCase.execute(Mono.just(new GetStaffByNameUseCase.Input(input.username)))
                        .result()
                        .flatMap(outputStaff -> {
                            if (!passwordEncoder.matches(input.password(), outputStaff.getPassword()))
                                return Mono.error(new CoreException("Wrong password"));

                            return customAuthentication.authenticate(outputStaff.getName(), outputStaff.getPassword());
                        })
        );

        return new Output(token);
    }

    public record Input(String username, String password) {

    }

    public record Output(Mono<String> accessToken) {}
}
