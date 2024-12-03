package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.applications.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.domain.exceptions.staff.StaffNotFoundException;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.security.admin.StaffDetails;
import com.takykulgam.ugur_v2.interfaces.mappers.EntityOutPutStaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
@Transactional(readOnly = true)
public class StaffRepositoryImpl implements StaffRepository {

    private final R2dbcStaffRepository staffRepository;
    private final EntityProcessor<StaffEntity> staffEntityProcessor;

    @Autowired
    public StaffRepositoryImpl(R2dbcStaffRepository staffRepository, EntityProcessor<StaffEntity> staffEntityProcessor) {
        this.staffRepository = staffRepository;
        this.staffEntityProcessor = staffEntityProcessor;
    }

    @Override
    public Mono<OutputStaff> findById(long id) {
        return staffRepository.findById(id)
                .map(EntityOutPutStaffMapper::toDto)
                .switchIfEmpty(Mono.error(new StaffNotFoundException("Staff with ID " + id + " not found")));
    }

    @Override
    public Mono<OutputStaff> findByName(String name) {
        return staffRepository.findByName(name)
                .map(EntityOutPutStaffMapper::toDto)
                .switchIfEmpty(Mono.error(new StaffNotFoundException("Staff with ID " + name + " not found")));
    }

    @Override
    @Transactional
    public Mono<OutputStaff> save(String name, String password, boolean isAdmin) {
        return Mono.just(new StaffEntity(name, password, isAdmin))
                .doOnNext(staffEntityProcessor::preprocessBeforeSave)
                .flatMap(staffRepository::save)
                .flatMap(entity -> Mono.just(EntityOutPutStaffMapper.toDto(entity)));
    }


    @Override
    @Transactional
    public Mono<OutputStaff> update(long id, final String name, final String password, final boolean isAdmin) {
        return staffRepository.findById(id)
                .map(entity -> {
                    entity.setName(Objects.isNull(name) ? entity.getName() : name);
                    entity.setPassword(Objects.isNull(password) ? entity.getPassword() : password);
                    entity.setAdmin(isAdmin);
                    return entity;})
                .doOnNext(staffEntityProcessor::preprocessBeforeUpdate)
                .flatMap(staffRepository::save)
                .flatMap(entity -> Mono.just(EntityOutPutStaffMapper.toDto(entity)));
    }


    @Override
    @Transactional
    public Mono<OutputStaff> updateMe(final String name, final String avatar, final String password) {
       return getAuthenticatedStaff()
        .flatMap(staffDetails -> Mono.just(staffDetails.getStaffEntity()))
        .flatMap(entity -> {
            System.out.println("Password: " + password);
            if (Objects.nonNull(avatar)) entity.setAvatar(avatar);
            if (Objects.nonNull(name)) entity.setName(name);
            if (Objects.nonNull(password) && !password.isBlank()) entity.setPassword(password);
            return Mono.just(entity);
        })
        .doOnNext(staffEntityProcessor::preprocessBeforeUpdate)
        .flatMap(staffRepository::save)
        .flatMap(entity -> Mono.just(EntityOutPutStaffMapper.toDto(entity)));
    }

    private Mono<StaffDetails> getAuthenticatedStaff() {
        return ReactiveSecurityContextHolder.getContext()
                .map(auth -> (StaffDetails) auth.getAuthentication().getPrincipal());
    }


    @Override
    public Mono<Void> delete(long id) {
        return staffRepository.deleteById(id);
    }

    @Override
    public Flux<OutputStaff> findAll() {
        return  staffRepository
                .findAll()
                .map(EntityOutPutStaffMapper::toDto);
    }

    @Override
    public Mono<Long> count() {
        return staffRepository.count();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return staffRepository.existsByName(name);
    }

}
