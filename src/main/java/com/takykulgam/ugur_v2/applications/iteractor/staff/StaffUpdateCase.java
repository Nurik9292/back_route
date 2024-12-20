package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.core.domain.entities.Staff;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StaffUpdateCase implements GenericUseCase<Mono<StaffUpdateCase.Input>, StaffUpdateCase.Output> {

    private final StaffRepository staffRepository;

    public StaffUpdateCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .map(Input::toStaff)
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
        return staffRepository.update(staff.getId(), staff.getName(), staff.getPassword().value(), staff.isAdmin());
    }

    public record Input(long id, String name, String password, boolean isAdmin) {
        public Staff toStaff() {
            return new Staff(id, name, password, isAdmin);
        }
    }

    public record Output(Mono<OutputStaff> result) {}
}
