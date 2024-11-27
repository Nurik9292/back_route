package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Staff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StaffAllPresenter implements Presenter<Flux<OutputStaff>,Mono<Response<ListStaffViewModel>>> {

    private Mono<Response<ListStaffViewModel>> response;

    @Override
    public Mono<Response<ListStaffViewModel>> getResponse() {
        return response;
    }

    @Override
    public Mono<Void> present(boolean success, Flux<OutputStaff> list) {
        Flux<Staff> staffFlux = list.map(outputStaff ->
                new Staff(outputStaff.getId(), outputStaff.getName(), outputStaff.isRole())
        );

        ListStaffViewModel listStaffViewModel = new ListStaffViewModel(staffFlux);

        response = Mono.just(new Response<>(success, listStaffViewModel));

        return Mono.empty();
    }
}