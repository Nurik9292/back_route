package com.takykulgam.ugur_v2.core.domain.gateways;

import com.takykulgam.ugur_v2.interfaces.dto.staff.OutputStaff;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface StaffRepository {

    Mono<OutputStaff> findById(long id);
    Mono<OutputStaff> findByName(String name);
    Mono<OutputStaff> save(String name, String password, boolean isAdmin);
    Mono<OutputStaff> update(long id, String name, String password, boolean isAdmin);
    Mono<Void> delete(long id);
    Flux<OutputStaff> findAll();
    Mono<Long> count();
    Mono<Boolean> existsByName(String name);
    Mono<OutputStaff> updateMe(String name, String avatar, String password);
    Mono<String> findByIdGetPassword(long id);
}

