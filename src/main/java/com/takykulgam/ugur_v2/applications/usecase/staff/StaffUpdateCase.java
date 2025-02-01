package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputStaffUpdate;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.entities.Staff;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StaffUpdateCase implements GenericUseCase<Mono<InputStaffUpdate>, StaffUpdateCase.Output> {

    private final StaffRepository staffRepository;

    public StaffUpdateCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<InputStaffUpdate> request) {
        return new Output(request
                .map(InputStaffUpdate::toEntity)
                .flatMap(this::validateStaff)
                .flatMap(this::updateStaff)
                .doOnSuccess(result -> log.info("Staff successfully update: {}", result))
                .doOnError(error -> log.error("Error update staff", error)));
    }

    private Mono<Staff> validateStaff(Staff staff) {
        staff.validateName();
        staff.validatePassword();
        return staff.checkExistName(staffRepository).thenReturn(staff);
    }

    private Mono<OutputStaff> updateStaff(Staff staff) {
        return staffRepository.update(staff.getId(), staff);
    }


    public record Output(Mono<OutputStaff> result) {}
}
