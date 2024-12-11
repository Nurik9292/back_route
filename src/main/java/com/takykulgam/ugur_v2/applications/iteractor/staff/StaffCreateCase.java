package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.entities.Staff;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import reactor.core.publisher.Mono;

public class StaffCreateCase implements GenericUseCase<Mono<StaffCreateCase.Input>, StaffCreateCase.Output> {

    private final StaffRepository staffRepository;
    private final ExistStaffUseCase existStaffUseCase;


    public StaffCreateCase(StaffRepository staffRepository, ExistStaffUseCase existStaffUseCase) {
        this.staffRepository = staffRepository;
        this.existStaffUseCase = existStaffUseCase;
    }



    @Override
    public Output execute(Mono<Input> request) {
        Mono<OutputStaff> outputStaff = request.flatMap(input ->
                existStaffUseCase.execute(Mono.just(new ExistStaffUseCase.Input(input.name())))
                        .isExist()
                        .flatMap(isExist -> {
                            if (isExist)
                                return Mono.error(new CoreException("Staff member with this name already exists."));
                            else new Staff(input.name(), input.password, input.isAdmin);
                            return staffRepository.save(input.name(), input.password(), input.isAdmin());
                        })
                );

        return new Output(outputStaff);
    }

    public record Input(String name, String password, boolean isAdmin) {

    }

    public record Output(Mono<OutputStaff> result) {}
}
