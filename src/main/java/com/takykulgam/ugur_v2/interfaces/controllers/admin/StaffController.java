package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffDelete;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffUpdateCase;
import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.RetrieveAllStaffCase;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffCreateCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/staff")
public class StaffController {

    private final Presenter<OutputStaff, Response<StaffViewModel>> presenter;
    private final Presenter<List<OutputStaff>, Response<ListStaffViewModel>> presenterAll;
    private final StaffCreateCase staffCreateCase;
    private final RetrieveAllStaffCase retrieveAllStaffCase;
    private final StaffUpdateCase staffUpdateCase;
    private final StaffDelete staffDelete;
    private final Presenter<String, Response<String>> presenterDelete;

    @Autowired
    public StaffController(
            Presenter<OutputStaff,
            Response<StaffViewModel>> presenter,
            Presenter<List<OutputStaff>,
            Response<ListStaffViewModel>> presenterAll,
            StaffCreateCase staffCreateCase,
            RetrieveAllStaffCase retrieveAllStaffCase,
            StaffUpdateCase staffUpdateCase,
            StaffDelete staffDelete,
            Presenter<String, Response<String>> presenterDelete) {
        this.presenter = presenter;
        this.presenterAll = presenterAll;
        this.staffCreateCase = staffCreateCase;
        this.retrieveAllStaffCase = retrieveAllStaffCase;
        this.staffUpdateCase = staffUpdateCase;
        this.staffDelete = staffDelete;
        this.presenterDelete = presenterDelete;
    }

    @GetMapping
    public ResponseEntity<Response<ListStaffViewModel>> getAllStaff() {
        retrieveAllStaffCase.execute();
        return ResponseEntity.ok(presenterAll.getResponse());
    }

    @PostMapping
    public ResponseEntity<Response<StaffViewModel>> createStaff(@RequestBody InputStaff inputStaff) {
        staffCreateCase.execute(inputStaff);
        return ResponseEntity.ok(presenter.getResponse());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<StaffViewModel>> updateStaff(@PathVariable Long id, @RequestBody InputStaff inputStaff) {
        inputStaff.setId(id);
        staffUpdateCase.execute(inputStaff);
        return ResponseEntity.ok(presenter.getResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<StaffViewModel>> deleteStaff(@PathVariable Long id) {
        staffDelete.execute(id);
        return ResponseEntity.ok(presenter.getResponse());
    }
}
