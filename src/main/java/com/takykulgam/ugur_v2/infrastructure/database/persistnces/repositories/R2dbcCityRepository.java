package com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.CityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R2dbcCityRepository extends R2dbcRepository<CityEntity, Long> {
}
