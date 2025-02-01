package com.takykulgam.ugur_v2.domain.gateways;

import com.takykulgam.ugur_v2.applications.boundaries.output.OutputStaff;
import com.takykulgam.ugur_v2.domain.entities.Domain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface StaffRepository {

    Mono<OutputStaff> findById(long id);
    Mono<OutputStaff> findByName(String name);
    Mono<OutputStaff> save(Domain domain);
    Mono<OutputStaff> update(long id, Domain domain);
    Mono<Void> delete(long id);
    Flux<OutputStaff> findAll();
    Mono<Long> count();
    Mono<Boolean> existsByName(String name);
    Mono<OutputStaff> updateMe(Domain domain, String avatar);
    Mono<String> findByIdGetPassword(long id);
}

