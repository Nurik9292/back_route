package com.takykulgam.ugur_v2.infrastructure.security.sessions;

import com.takykulgam.ugur_v2.applications.security.SessionManager;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffSessionEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionManagerImpl implements SessionManager {

    private final JpaStaffSessionRepository jpaStaffSessionRepository;

    @Autowired
    public SessionManagerImpl(JpaStaffSessionRepository jpaStaffSessionRepository) {
        this.jpaStaffSessionRepository = jpaStaffSessionRepository;
    }

    @Override
    public StaffSessionEntity refreshSession(SessionUser sessionUser) {
        return  actionStaff((StaffEntity) sessionUser);
    }


    private StaffSessionEntity actionStaff(StaffEntity staff) {
        StaffSessionEntity session = staff.getSession();
        Optional.ofNullable(session).ifPresent(jpaStaffSessionRepository::delete);
        StaffSessionEntity newSession = new StaffSessionEntity();
        newSession.setStaff(staff);

        return newSession;
    }
}
