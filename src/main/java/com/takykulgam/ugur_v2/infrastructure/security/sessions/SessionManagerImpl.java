package com.takykulgam.ugur_v2.infrastructure.security.sessions;

import com.takykulgam.ugur_v2.applications.security.SessionManager;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffSessionEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcStaffSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.EnumMap;

@Component
public class SessionManagerImpl implements SessionManager {

    private final R2dbcStaffSessionRepository jpaStaffSessionRepository;

    @Autowired
    public SessionManagerImpl(R2dbcStaffSessionRepository jpaStaffSessionRepository) {
        this.jpaStaffSessionRepository = jpaStaffSessionRepository;

        commands.put(EntitySession.STAFF, (user) ->
            Mono
                .just(user)
                .cast(StaffEntity.class)
                .flatMap(staff ->  jpaStaffSessionRepository.deleteByStaffId(staff.getId()).thenReturn(staff.getId()))
                .flatMap(staffId -> {
                StaffSessionEntity newSession = new StaffSessionEntity();
                newSession.setStaffId(staffId);
                return Mono.just(newSession);
                })
        );
    }

    @Override
    public Mono<StaffSessionEntity> refreshSession(SessionUser sessionUser, EntitySession entitySession) {
        return Mono.just(sessionUser)
                .cast(StaffEntity.class)
                .flatMap(staffEntity -> commands.get(entitySession).action(staffEntity));
    }


    public enum EntitySession {
        STAFF
    }

    interface Command {
        Mono<StaffSessionEntity> action(SessionUser sessionUser);
    }

    private final EnumMap<EntitySession, Command> commands = new EnumMap<>(EntitySession.class);

}
