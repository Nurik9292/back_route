package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import reactor.core.publisher.Mono;

public class StaffDeletePresenter implements Presenter<String, Mono<Response<String>>> {

    private Mono<Response<String>> response;

    @Override
    public Mono<Response<String>> getResponse() {
        return response;
    }

    @Override
    public Mono<Void> present(boolean success, String item) {
        response = Mono.just(new Response<>(success, item));
        return Mono.empty();
    }
}
