package com.takykulgam.ugur_v2.infrastructure.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.RouteDirectionEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R2dbcRouteDirectionRepository extends R2dbcRepository<RouteDirectionEntity, Long> {
}
