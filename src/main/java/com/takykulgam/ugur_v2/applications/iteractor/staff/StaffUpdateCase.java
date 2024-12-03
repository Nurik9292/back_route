package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.applications.dto.OutputStaff;
import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import reactor.core.publisher.Mono;


public class StaffUpdateCase implements GenericUseCase<Mono<StaffUpdateCase.Input>, StaffUpdateCase.Output> {

    private final GetStaffByIdUseCase getStaffByIdUseCase;
    private final StaffRepository staffRepository;

    public StaffUpdateCase(GetStaffByIdUseCase getStaffByIdUseCase,
                           StaffRepository staffRepository) {
        this.getStaffByIdUseCase = getStaffByIdUseCase;
        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<Input> request) {
        Mono<OutputStaff> outputStaff = request.flatMap(input -> getStaffByIdUseCase.execute(Mono.just(new GetStaffByIdUseCase.Input(input.id)))
                .result()
                .switchIfEmpty(Mono.error(new CoreException("User not found")))
                .flatMap(staff -> request.flatMap(req -> staffRepository.update(staff.getId(), req.name, req.password, req.isAdmin)))
                );

        return new Output(outputStaff);
    }

    public record Input(long id, String name, String password, boolean isAdmin) {
    }

    public record Output(Mono<OutputStaff> result) {}
}
