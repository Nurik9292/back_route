package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import reactor.core.publisher.Mono;

public class StaffGetByNameUseCase implements GenericUseCase<Mono<String>, StaffGetByNameUseCase.Output> {

    private final StaffRepository staffRepository;

    public StaffGetByNameUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Output execute(Mono<String> request) {
        return new Output(request.flatMap(this.staffRepository::findByName));
    }

    public record Output(Mono<OutputStaff> result) { }
}
