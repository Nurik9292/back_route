package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import reactor.core.publisher.Mono;

public class StaffExistUseCase implements GenericUseCase<Mono<String>, StaffExistUseCase.Output> {

    private final StaffRepository staffRepository;

    public StaffExistUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Output execute(Mono<String> request) {
        return new Output(request.flatMap(staffRepository::existsByName));
    }

    public record Output(Mono<Boolean> isExist) {}
}
