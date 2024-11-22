package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;

public class StaffDeletePresenter implements Presenter<String, Response<String>> {

    private Response<String> response;

    @Override
    public Response<String> getResponse() {
        return response;
    }

    @Override
    public void present(boolean success, String item) {
        response = new Response<>(success, item);
    }
}
