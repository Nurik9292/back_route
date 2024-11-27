package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffDeleteCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import reactor.core.publisher.Mono;

public class StaffDeleteCaseImpl implements StaffDeleteCase {

    private final StaffRepository staffRepository;
    private final Presenter<String, Mono<Response<String>>> presenter;

    public StaffDeleteCaseImpl(StaffRepository staffRepository, Presenter<String,Mono< Response<String>>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }

    @Override
    public Mono<Void> execute(long id) {
        return staffRepository.delete(id).then(presenter.present(true, "Staff Delete"));
    }
}
