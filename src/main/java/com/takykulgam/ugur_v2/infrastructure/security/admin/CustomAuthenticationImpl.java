package com.takykulgam.ugur_v2.infrastructure.security.admin;

import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.JwtTokenProvider;
import com.takykulgam.ugur_v2.applications.security.SessionManager;
import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationImpl implements CustomAuthentication {

    private final StaffDetailsService staffDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionManager sessionManager;
    private final JpaStaffSessionRepository jpaStaffSessionRepository;
    private final ReactiveAuthenticationManager authenticationManager;


    @Autowired
    public CustomAuthenticationImpl(
            StaffDetailsService staffDetailsService,
            JwtTokenProvider jwtTokenProvider,
            SessionManager sessionManager,
            JpaStaffSessionRepository jpaStaffSessionRepository,
            ReactiveAuthenticationManager authenticationManager) {
        this.staffDetailsService = staffDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.sessionManager = sessionManager;
        this.jpaStaffSessionRepository = jpaStaffSessionRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<String> authenticate(String username, String password) {
        return staffDetailsService.loadUserByUsernameReactive(username)
                .flatMap(this::session)
                .flatMap(this::authenticate);
    }


    private Mono<String> session(StaffDetails staffDetails) {
        String token = jwtTokenProvider.generateToken(staffDetails);
        return sessionManager.refreshSession(staffDetails.getStaffEntity())
                .flatMap(session -> {
                    session.setStaffId(staffDetails.getStaffEntity().getId());
                    session.setToken(token);
                   return jpaStaffSessionRepository.save(session).thenReturn(token);
                });
    }

    private Mono<String> authenticate(String token) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(token, token))
                .thenReturn(token);
    }
}
