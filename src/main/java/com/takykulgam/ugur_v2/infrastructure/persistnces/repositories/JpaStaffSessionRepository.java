package com.takykulgam.ugur_v2.infrastructure.persistnces.repositories;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStaffSessionRepository extends JpaRepository<StaffSessionEntity, Long> {
}
