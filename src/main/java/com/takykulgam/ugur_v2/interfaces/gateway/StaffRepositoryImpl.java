package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffNotFoundException;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffRepository;
import com.takykulgam.ugur_v2.interfaces.mappers.InputStaffToStaffEntityMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.StaffEntityToOutputStaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StaffRepositoryImpl implements StaffRepository {

    private final JpaStaffRepository staffRepository;
    private final EntityProcessor<StaffEntity> staffEntityProcessor;

    @Autowired
    public StaffRepositoryImpl(JpaStaffRepository staffRepository, EntityProcessor<StaffEntity> staffEntityProcessor) {
        this.staffRepository = staffRepository;
        this.staffEntityProcessor = staffEntityProcessor;
    }

    @Override
    public OutputStaff findById(long id) {
        return StaffEntityToOutputStaffMapper.toOutputStaff(staffRepository.findById(id).orElseThrow());
    }

    @Override
    public OutputStaff findByName(String name) {
        return StaffEntityToOutputStaffMapper.toOutputStaff(staffRepository.findByName(name)
                .orElseThrow(() -> new StaffNotFoundException("Staff with name '" + name + "' not found")));
    }

    @Override
    public OutputStaff save(InputStaff staff) {
        StaffEntity staffEntity = InputStaffToStaffEntityMapper.toEntity(staff);
        staffEntityProcessor.preprocessBeforeSave(staffEntity);
        return StaffEntityToOutputStaffMapper.toOutputStaff(staffRepository.save(staffEntity));
    }

    @Override
    public OutputStaff update(InputStaff staff) {
        StaffEntity updatedStaffEntity = staffRepository.findById(staff.getId())
                .map(staffEntity -> {
                    staffEntityProcessor.preprocessBeforeUpdate(staffEntity);

                    Optional.ofNullable(staff.getName())
                            .filter(name -> !name.isEmpty())
                            .ifPresent(staffEntity::setName);

                    Optional.ofNullable(staff.getPassword())
                            .filter(password -> !password.isEmpty())
                            .ifPresent(staffEntity::setPassword);

                    if (staff.isAdmin() != staffEntity.isAdmin()) {
                        staffEntity.setAdmin(staff.isAdmin());
                    }

                    return staffRepository.save(staffEntity);
                })
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));

        return StaffEntityToOutputStaffMapper.toOutputStaff(updatedStaffEntity);
    }

    @Override
    public void delete(long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public List<OutputStaff> findAll() {
        return staffRepository.findAll().stream().map(StaffEntityToOutputStaffMapper::toOutputStaff).toList();
    }

    @Override
    public long count() {
        return staffRepository.count();
    }
}
