package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffUpdateCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.core.domain.entities.staff.Staff;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import reactor.core.publisher.Mono;


public class StaffUpdateCaseImpl implements StaffUpdateCase {

    private final StaffRepository staffRepository;
    private final Presenter<OutputStaff, Mono<Response<StaffViewModel>>> presenter;

    public StaffUpdateCaseImpl(StaffRepository staffRepository,
                               Presenter<OutputStaff, Mono<Response<StaffViewModel>>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }

    @Override
    public Mono<Void> execute(Mono<InputStaff> input) {
        return input
                .map(inputStaff -> {
                    Staff staff = new Staff(inputStaff.getName(), inputStaff.getPassword(), inputStaff.isAdmin());
                    staff.validateName();
                    staff.validatePassword();
                    return inputStaff;
                })
                .flatMap(inputStaff -> staffRepository.update(Mono.just(inputStaff)))
                .flatMap(outputStaff -> presenter.present(true, outputStaff))
                .onErrorResume(e ->
                        presenter.present(false, null).then(Mono.error(new RuntimeException("Error updating staff", e)))
                );
    }
}
