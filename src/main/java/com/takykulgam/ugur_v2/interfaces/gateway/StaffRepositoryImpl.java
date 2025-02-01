package com.takykulgam.ugur_v2.interfaces.gateway;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.security.admin.StaffDetails;
import com.takykulgam.ugur_v2.interfaces.mappers.domainEntity.DomainEntityStaffMapper;
import com.takykulgam.ugur_v2.interfaces.mappers.entityOutput.EntityOutputStaffMapper;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Transactional(readOnly = true)
public class StaffRepositoryImpl implements StaffRepository {

    private final R2dbcStaffRepository staffRepository;
    private final EntityProcessor<StaffEntity> staffEntityProcessor;
    private final EntityOutputStaffMapper entityOutputStaffMapper;
    private final DomainEntityStaffMapper domainEntityStaffMapper;

    public StaffRepositoryImpl(R2dbcStaffRepository staffRepository,
                               EntityProcessor<StaffEntity> staffEntityProcessor,
                               EntityOutputStaffMapper entityOutputStaffMapper,
                               DomainEntityStaffMapper domainEntityStaffMapper) {
        this.staffRepository = staffRepository;
        this.staffEntityProcessor = staffEntityProcessor;
        this.entityOutputStaffMapper = entityOutputStaffMapper;
        this.domainEntityStaffMapper = domainEntityStaffMapper;
    }

    @Override
    public Mono<OutputStaff> findById(long id) {
        return staffRepository.findById(id)
                .map(entityOutputStaffMapper::toDto)
                .switchIfEmpty(Mono.error(new CoreException("Staff with ID %d not found".formatted(id))));
    }

    @Override
    public Mono<OutputStaff> findByName(String name) {
        return staffRepository.findByName(name)
                .map(entityOutputStaffMapper::toDto)
                .switchIfEmpty(Mono.error(new CoreException("Staff with ID %s not found".formatted(name))));
    }

    @Override
    @Transactional
    public Mono<OutputStaff> save(Domain domain) {
        return Mono.just(domainEntityStaffMapper.toEntity(domain))
                .doOnNext(staffEntityProcessor::preprocessBeforeSave)
                .flatMap(staffRepository::save)
                .map(entityOutputStaffMapper::toDto);
    }


    @Override
    @Transactional
    public Mono<OutputStaff> update(long id, Domain domain) {
        return staffRepository.findById(id)
                .map(entity -> {
                    StaffEntity staffEntity = domainEntityStaffMapper.toEntity(domain);
                    entity.setName(Objects.isNull(staffEntity.getName()) ? entity.getName() : staffEntity.getName());
                    entity.setPassword(Objects.isNull(staffEntity.getPassword()) ? entity.getPassword() : staffEntity.getPassword());
                    entity.setAdmin(staffEntity.isAdmin());
                    return entity;})
                .doOnNext(staffEntityProcessor::preprocessBeforeUpdate)
                .flatMap(staffRepository::save)
                .map(entityOutputStaffMapper::toDto);
    }


    @Override
    @Transactional
    public Mono<OutputStaff> updateMe(Domain domain, String avatar) {
       return getAuthenticatedStaff()
        .flatMap(staffDetails -> Mono.just(staffDetails.getStaffEntity()))
        .flatMap(entity -> {
            StaffEntity staffEntity = domainEntityStaffMapper.toEntity(domain);
            if (Objects.nonNull(avatar)) entity.setAvatar(avatar);
            if (Objects.nonNull(staffEntity.getName())) entity.setName(staffEntity.getName());
            if (Objects.nonNull(staffEntity.getPassword()) && !staffEntity.getPassword().isBlank()) entity.setPassword(staffEntity.getPassword());
            return Mono.just(entity);
        })
        .doOnNext(staffEntityProcessor::preprocessBeforeUpdate)
        .flatMap(staffRepository::save)
        .map(entityOutputStaffMapper::toDto);
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
                .map(entityOutputStaffMapper::toDto);
    }

    @Override
    public Mono<Long> count() {
        return staffRepository.count();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return staffRepository.existsByName(name);
    }


    @Override
    public Mono<String> findByIdGetPassword(long id) {
        return staffRepository.findByIdGetPassword(id);
    }

}
