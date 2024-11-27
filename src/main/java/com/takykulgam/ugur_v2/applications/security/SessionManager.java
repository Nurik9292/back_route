package com.takykulgam.ugur_v2.applications.security;

import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffSessionEntity;
import com.takykulgam.ugur_v2.infrastructure.security.sessions.SessionUser;
import reactor.core.publisher.Mono;

public interface SessionManager {
     Mono<StaffSessionEntity> refreshSession(SessionUser sessionUser);
}
