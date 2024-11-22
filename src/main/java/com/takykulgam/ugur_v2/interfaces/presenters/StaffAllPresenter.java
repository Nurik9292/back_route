package com.takykulgam.ugur_v2.interfaces.presenters;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Staff;
import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;

import java.util.List;
import java.util.stream.Collectors;

public class StaffAllPresenter implements Presenter<List<OutputStaff>, Response<ListStaffViewModel>> {

    private Response<ListStaffViewModel>  response;

    @Override
    public Response<ListStaffViewModel> getResponse() {
        return response;
    }

    @Override
    public void present(boolean success, List<OutputStaff> list) {
        List<Staff> staffList = list.stream()
                .map(outputStaff -> new Staff(
                        outputStaff.getId(),
                        outputStaff.getName(),
                        outputStaff.isRole()
                ))
                .collect(Collectors.toList());
        response = new Response<>(success, new ListStaffViewModel(staffList));
    }
}
