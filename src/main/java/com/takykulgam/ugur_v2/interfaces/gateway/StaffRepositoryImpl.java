package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffNotFoundException;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffRepository;
import com.takykulgam.ugur_v2.interfaces.mappers.StaffEntityToOutputStaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<OutputStaff> findById(long id) {
        return staffRepository.findById(id)
                .map(StaffEntityToOutputStaffMapper::toOutputStaff)
                .switchIfEmpty(Mono.error(new StaffNotFoundException("Staff with ID " + id + " not found")));
    }

    @Override
    public Mono<OutputStaff> findByName(String name) {
        return staffRepository.findByName(name).map(StaffEntityToOutputStaffMapper::toOutputStaff)
                .switchIfEmpty(Mono.error(new StaffNotFoundException("Staff with ID " + name + " not found")));
    }

    @Override
    public Mono<OutputStaff> save(Mono<InputStaff> staff) {
        return staff
                .cast(StaffEntity.class)
                .doOnNext(staffEntityProcessor::preprocessBeforeSave)
                .flatMap(staffRepository::save)
                .cast(OutputStaff.class);
    }


    @Override
    public Mono<OutputStaff> update(Mono<InputStaff> input) {
        return input
                .flatMap(staff -> staffRepository.findById(staff.getId())
                        .switchIfEmpty(Mono.error(new StaffNotFoundException("Staff with ID " + staff.getId() + " not found")))
                        .flatMap(staffEntity -> {
                            staffEntityProcessor.preprocessBeforeUpdate(staffEntity);

                            if (staff.getName() != null && !staff.getName().isEmpty()) {
                                staffEntity.setName(staff.getName());
                            }
                            if (staff.getPassword() != null && !staff.getPassword().isEmpty()) {
                                staffEntity.setPassword(staff.getPassword());
                            }
                            if (staff.isAdmin() != staffEntity.isAdmin()) {
                                staffEntity.setAdmin(staff.isAdmin());
                            }

                            return staffRepository.save(staffEntity);
                        })
                        .map(StaffEntityToOutputStaffMapper::toOutputStaff)
                )
                .onErrorResume(e -> {
                    if (e instanceof StaffNotFoundException) {
                        return Mono.error(e);
                    }
                    return Mono.error(new RuntimeException("An error occurred while updating staff", e));
                });
    }


    @Override
    public Mono<Void> delete(long id) {
        return staffRepository.deleteById(id);
    }

    @Override
    public Flux<OutputStaff> findAll() {
        return staffRepository.findAll().map(StaffEntityToOutputStaffMapper::toOutputStaff);
    }

    @Override
    public Mono<Long> count() {
        return staffRepository.count();
    }

    @Override
    public Mono<String> passwordHash(long id) {
        return staffRepository.findById(id).map(StaffEntity::getPassword);
    }
}
