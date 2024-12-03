package com.takykulgam.ugur_v2.infrastructure.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface R2dbcStaffRepository extends R2dbcRepository<StaffEntity, Long> {
    Mono<StaffEntity> findByName(String username);
    Mono<Boolean> existsByName(String username);
}
