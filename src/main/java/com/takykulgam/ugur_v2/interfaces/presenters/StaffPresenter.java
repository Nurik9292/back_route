package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Staff;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;

public class StaffPresenter implements Presenter<OutputStaff, Response<StaffViewModel>> {

    private Response<StaffViewModel> response;

    public StaffPresenter() {}

    @Override
    public Response<StaffViewModel> getResponse() {
        return response;
    }

    @Override
    public void present(boolean success, OutputStaff data) {
        Staff staff = new Staff(data.getId(), data.getName(), data.isRole());
        response = new Response<>(success, new StaffViewModel(staff));
    }
}
