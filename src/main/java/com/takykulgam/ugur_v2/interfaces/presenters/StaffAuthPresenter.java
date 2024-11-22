package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;

public class StaffAuthPresenter implements Presenter<String, Response<StaffAuthViewModel>> {

    private Response<StaffAuthViewModel> response;

    @Override
    public Response<StaffAuthViewModel> getResponse() {
        return response;
    }

    @Override
    public void present(boolean success, String item) {
        response = new Response<>(success, new StaffAuthViewModel(item));
    }
}
