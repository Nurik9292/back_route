package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import reactor.core.publisher.Mono;

public class StaffAuthPresenter implements Presenter<String, Mono<Response<StaffAuthViewModel>>> {

    private Mono<Response<StaffAuthViewModel>> response;

    @Override
    public Mono<Response<StaffAuthViewModel>> getResponse() {
        return response;
    }

    @Override
    public Mono<Void> present(boolean success, String item) {
        response = Mono.just(new Response<>(success, new StaffAuthViewModel(item)));
        return Mono.empty();
    }
}
