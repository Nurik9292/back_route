package com.takykulgam.ugur_v2.applications.usecase.staff;

import com.takykulgam.ugur_v2.applications.boundaries.input.staff.InputStaffCreate;
import com.takykulgam.ugur_v2.applications.usecase.GenericUseCase;
import com.takykulgam.ugur_v2.domain.entities.Staff;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StaffCreateCase implements GenericUseCase<Mono<InputStaffCreate>, StaffCreateCase.Output> {

    private final StaffRepository staffRepository;


    public StaffCreateCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<InputStaffCreate> request) {
        return new Output(request
                .map(InputStaffCreate::toEntity)
                .flatMap(this::validateStaff)
                .flatMap(this::saveStaff)
                .doOnSuccess(result -> log.info("Staff successfully store: {}", result))
                .doOnError(error -> log.error("Error store staff", error)));
    }

    private Mono<Staff> validateStaff(Staff staff) {
        staff.validateName();
        staff.validatePassword();
        return staff.checkExistName(staffRepository).thenReturn(staff);
    }

    private Mono<OutputStaff> saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }


    public record Output(Mono<OutputStaff> result) {}
}
