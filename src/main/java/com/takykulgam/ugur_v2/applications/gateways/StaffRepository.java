package com.takykulgam.ugur_v2.applications.gateways;

import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;

import java.util.List;

public interface StaffRepository {

    OutputStaff findById(long id);
    OutputStaff findByName(String name);
    OutputStaff save(InputStaff staff);
    OutputStaff update(InputStaff staff);
    void delete(long id);
    List<OutputStaff> findAll();
    long count();
}

