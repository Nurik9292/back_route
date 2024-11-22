package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.RetrieveAllStaffCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;

import java.util.List;

public class RetrieveAllStaffCaseImpl implements RetrieveAllStaffCase {

    private final StaffRepository staffRepository;
    private final Presenter<List<OutputStaff>, Response<ListStaffViewModel>> presenter;

    public RetrieveAllStaffCaseImpl(StaffRepository staffRepository,
                                    Presenter<List<OutputStaff>, Response<ListStaffViewModel>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.present(true, staffRepository.findAll());
    }
}
