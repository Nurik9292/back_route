package com.takykulgam.ugur_v2.applications.iteractor;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.boundaries.dto.AuthStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.auth.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;

public class AuthStaffLoginCaseImpl implements AuthStaffLoginCase {

    private final StaffRepository staffRepository;
    private final CustomerPasswordEncoder passwordEncoder;
    private final CustomAuthentication customAuthentication;
    private final Presenter<String, Response<StaffAuthViewModel>> presenter;

    public AuthStaffLoginCaseImpl(StaffRepository staffRepository,
                                  CustomerPasswordEncoder passwordEncoder,
                                  CustomAuthentication customAuthentication,
                                  Presenter<String, Response<StaffAuthViewModel>> presenter) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
        this.customAuthentication = customAuthentication;
        this.presenter = presenter;

    }

    @Override
    public void login(AuthStaff staff) {
        OutputStaff existStaff = staffRepository.findByName(staff.getName());
        if(passwordEncoder.matches(staff.getPassword(), existStaff.getPassword()))
            presenter.present(true, customAuthentication.authenticate(existStaff.getName(), staff.getPassword()));
        else
            presenter.present(false, "Wrong password");
    }
}
