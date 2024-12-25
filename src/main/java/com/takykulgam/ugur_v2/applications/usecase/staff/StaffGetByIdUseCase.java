package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import reactor.core.publisher.Mono;

public class StaffGetByIdUseCase implements GenericUseCase<Mono<Long>, StaffGetByIdUseCase.Output> {

    private final StaffRepository staffRepository;

    public StaffGetByIdUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Output execute(Mono<Long> request) {
        return new Output(request.flatMap(staffRepository::findById));
    }

    public record Output(Mono<OutputStaff> result) { }
}
