package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.domain.entities.staff.Staff;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffCreateCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import reactor.core.publisher.Mono;

public class StaffCreateCaseImpl implements StaffCreateCase {

    private final StaffRepository staffRepository;
    private final Presenter<OutputStaff, Mono<Response<StaffViewModel>>> presenter;


    public StaffCreateCaseImpl(StaffRepository staffRepository,
                               Presenter<OutputStaff, Mono<Response<StaffViewModel>>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }


    @Override
    public Mono<Void> execute(Mono<InputStaff> inputMono) {
        return inputMono
                .map(input -> {
                    Staff staff = new Staff(input.getName(), input.getPassword(), input.isAdmin());
                    staff.validateName();
                    staff.validatePassword();
                    return input;
                })
                .flatMap(inputStaff -> staffRepository.save(Mono.just(inputStaff)))
                .flatMap(outputStaff -> presenter.present(true, outputStaff).then())
                .onErrorResume(e -> presenter.present(false, null).then(Mono.error(new RuntimeException("Error creating staff", e))));
    }
}
