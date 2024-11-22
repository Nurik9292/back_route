package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffDelete;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;

public class StaffDeleteImpl implements StaffDelete {

    private final StaffRepository staffRepository;
    private final Presenter<String, Response<String>> presenter;

    public StaffDeleteImpl(StaffRepository staffRepository, Presenter<String, Response<String>> presenter) {
        this.staffRepository = staffRepository;
        this.presenter = presenter;
    }

    @Override
    public void execute(long id) {
        staffRepository.delete(id);
        presenter.present(true, "Staff Delete");
    }
}
