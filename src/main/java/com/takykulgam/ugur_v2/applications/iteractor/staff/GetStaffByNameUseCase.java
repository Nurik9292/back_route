package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import reactor.core.publisher.Mono;

public class GetStaffByNameUseCase implements GenericUseCase<Mono<GetStaffByNameUseCase.Input>, GetStaffByNameUseCase.Output> {

    private final StaffRepository staffRepository;

    public GetStaffByNameUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request.map(Input::userName).flatMap(this.staffRepository::findByName));
    }

    public record Input(String userName) { }

    public record Output(Mono<OutputStaff> result) { }
}
