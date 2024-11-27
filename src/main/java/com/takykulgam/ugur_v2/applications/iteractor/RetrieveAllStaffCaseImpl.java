package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.RetrieveAllStaffCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RetrieveAllStaffCaseImpl implements RetrieveAllStaffCase {

    private final StaffRepository staffRepository;
    private final Presenter<Flux<OutputStaff>, Mono<Response<ListStaffViewModel>>> presenter;

    public RetrieveAllStaffCaseImpl(StaffRepository staffRepository,
                                    Presenter<Flux<OutputStaff>, Mono<Response<ListStaffViewModel>>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }

    @Override
    public Mono<Void> execute()
    {
       return presenter.present(true,  staffRepository.findAll());
    }

   
}
