package com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StaffSessionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcStaffSessionRepository extends R2dbcRepository<StaffSessionEntity, Long> {

    @Query("SELECT * FROM staff_sessions WHERE staff_id = :staffId")
    Mono<StaffSessionEntity> findByStaffId(Long staffId);

    @Query("DELETE FROM staff_sessions WHERE staff_id = :staffId")
    Mono<Void> deleteByStaffId(Long staffId);
}
