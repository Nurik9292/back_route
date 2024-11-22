package com.takykulgam.ugur_v2.applications.security;

import com.takykulgam.ugur_v2.infrastructure.security.sessions.Session;
import com.takykulgam.ugur_v2.infrastructure.security.sessions.SessionUser;

public interface SessionManager {
     Session refreshSession(SessionUser sessionUser);
}
