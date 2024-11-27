package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Staff;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import reactor.core.publisher.Mono;

public class StaffPresenter implements Presenter<OutputStaff, Mono<Response<StaffViewModel>>> {

    private Mono<Response<StaffViewModel>> response;

    public StaffPresenter() {}

    @Override
    public Mono<Response<StaffViewModel>> getResponse() {
        return response;
    }

    @Override
    public Mono<Void> present(boolean success, OutputStaff data) {
        Staff staff = new Staff(data.getId(), data.getName(), data.isRole());
        response = Mono.just(new Response<>(success, new StaffViewModel(staff)));
        return Mono.empty();
    }
}
