package com.takykulgam.ugur_v2.applications.gateways;

import com.takykulgam.ugur_v2.core.boundaries.dto.InputStaff;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StaffRepository {

    Mono<OutputStaff> findById(long id);
    Mono<OutputStaff> findByName(String name);
    Mono<OutputStaff> save(Mono<InputStaff> staff);
    Mono<OutputStaff> update(Mono<InputStaff> staff);
    Mono<Void> delete(long id);
    Flux<OutputStaff> findAll();
    Mono<Long> count();
    Mono<String> passwordHash(long id);
}

