package com.takykulgam.ugur_v2.applications.iteractor.staff;

import com.takykulgam.ugur_v2.core.boundaries.input.GenericUseCase;
import com.takykulgam.ugur_v2.core.domain.entities.Staff;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.output.OutputStaff;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class StaffCreateCase implements GenericUseCase<Mono<StaffCreateCase.Input>, StaffCreateCase.Output> {

    private final StaffRepository staffRepository;


    public StaffCreateCase(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    @Override
    public Output execute(Mono<Input> request) {
        return new Output(request
                .map(Input::toStaff)
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
        return staffRepository.save(staff.getName(), staff.getPassword().value(), staff.isAdmin());
    }


    public record Input(String name, String password, boolean isAdmin) {
        public Staff toStaff() {
            return new Staff(name, password, isAdmin);
        }
    }

    public record Output(Mono<OutputStaff> result) {}
}
