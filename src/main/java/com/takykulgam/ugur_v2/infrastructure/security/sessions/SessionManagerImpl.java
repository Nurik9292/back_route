package com.takykulgam.ugur_v2.infrastructure.security.sessions;

import com.takykulgam.ugur_v2.applications.security.SessionManager;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffSessionEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcStaffSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SessionManagerImpl implements SessionManager {

    private final R2dbcStaffSessionRepository jpaStaffSessionRepository;

    @Autowired
    public SessionManagerImpl(R2dbcStaffSessionRepository jpaStaffSessionRepository) {
        this.jpaStaffSessionRepository = jpaStaffSessionRepository;
    }

    @Override
    public Mono<StaffSessionEntity> refreshSession(SessionUser sessionUser) {
        return Mono.just(sessionUser)
                .cast(StaffEntity.class)
                .flatMap(this::actionStaff);
    }

    private Mono<StaffSessionEntity> actionStaff(StaffEntity staff) {
        return Mono.justOrEmpty(staff)
                .flatMap(staffEntity ->  jpaStaffSessionRepository.deleteByStaffId(staffEntity.getId()))
                .then(Mono.defer(() -> {
                    StaffSessionEntity newSession = new StaffSessionEntity();
                    newSession.setStaffId(staff.getId());
                    return Mono.just(newSession);
                }));
    }
}
