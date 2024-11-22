package com.takykulgam.ugur_v2.infrastructure.security.admin;

import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.JwtToken;
import com.takykulgam.ugur_v2.applications.security.SessionManager;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffSessionEntity;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomAuthenticationImpl implements CustomAuthentication {

    private final AuthenticationManager authenticationManager;
    private final StaffDetailsService staffDetailsService;
    private final JwtToken jwtTokenProvider;
    private final SessionManager sessionManager;
    private final JpaStaffSessionRepository jpaStaffSessionRepository;


    @Autowired
    public CustomAuthenticationImpl(
            AuthenticationManager authenticationManager,
            StaffDetailsService staffDetailsService,
            JwtToken jwtTokenProvider,
            SessionManager sessionManager,
            JpaStaffSessionRepository jpaStaffSessionRepository) {
        this.authenticationManager = authenticationManager;
        this.staffDetailsService = staffDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.sessionManager = sessionManager;
        this.jpaStaffSessionRepository = jpaStaffSessionRepository;
    }

    @Transactional
    @Override
    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        StaffDetails staffDetails = staffDetailsService.loadUserByUsername(username);
        StaffSessionEntity session = (StaffSessionEntity) sessionManager.refreshSession(staffDetails.getStaffEntity());
        String token = jwtTokenProvider.generateToken(staffDetails);
        session.setToken(token);
        jpaStaffSessionRepository.saveAndFlush(session);
        return token;
    }
}
