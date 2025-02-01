package com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.RouteDirectionEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R2dbcRouteDirectionRepository extends R2dbcRepository<RouteDirectionEntity, Long> {
}
