package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import reactor.core.publisher.Mono;

public class GetStaffByIdUseCase implements GenericUseCase<Mono<GetStaffByIdUseCase.Input>, GetStaffByIdUseCase.Output> {

    private final StaffRepository staffRepository;

    public GetStaffByIdUseCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Output execute(Mono<Input> request) {
        return new Output(
                request
                        .map(Input::id)
                        .flatMap(staffRepository::findById));
    }

    public record Input(long id) { }

    public record Output(Mono<OutputStaff> result) { }
}
