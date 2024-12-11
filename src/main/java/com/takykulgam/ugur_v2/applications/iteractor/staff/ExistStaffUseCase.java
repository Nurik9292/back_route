package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import reactor.core.publisher.Mono;

public class ExistStaffUseCase implements GenericUseCase<Mono<ExistStaffUseCase.Input>, ExistStaffUseCase.Output> {

    private final StaffRepository staffRepository;

    public ExistStaffUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request.map(Input::name).flatMap(staffRepository::existsByName));
    }

    public record Input(String name) {}

    public record Output(Mono<Boolean> isExist) {}
}
