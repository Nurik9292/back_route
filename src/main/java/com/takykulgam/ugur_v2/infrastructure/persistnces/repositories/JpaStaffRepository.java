package com.takykulgam.ugur_v2.infrastructure.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaStaffRepository extends JpaRepository<StaffEntity, Long> {

    Optional<StaffEntity> findByName(String username);
}
