package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.domain.entities.staff.Staff;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffCreateCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;

public class StaffCreateCaseImpl implements StaffCreateCase {

    private final StaffRepository staffRepository;
    private final Presenter<OutputStaff, Response<StaffViewModel>> presenter;


    public StaffCreateCaseImpl(StaffRepository staffRepository,
                               Presenter<OutputStaff, Response<StaffViewModel>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }


    @Override
    public void execute(InputStaff input) {
        Staff staff = new Staff(input.getName(), input.getPassword(), input.isAdmin());
        staff.validateName();
        staff.validatePassword();
        presenter.present(true, staffRepository.save(input));
    }
}
