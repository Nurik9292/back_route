package com.takykulgam.ugur_v2.infrastructure.security.admin;

import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.R2dbcStaffSessionRepository;
import com.takykulgam.ugur_v2.infrastructure.security.JwtTokenProviderImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AdminAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProviderImpl jwtTokenProvider;
    private final StaffDetailsService staffDetailsService;
    private final R2dbcStaffSessionRepository jpaStaffSessionRepository;

    @Autowired
    public AdminAuthenticationManager(JwtTokenProviderImpl jwtTokenProvider,
                                      StaffDetailsService staffDetailsService,
                                      R2dbcStaffSessionRepository jpaStaffSessionRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.staffDetailsService = staffDetailsService;
        this.jpaStaffSessionRepository = jpaStaffSessionRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        return Mono.just(token)
                .flatMap(this::validateToken)
                .flatMap(this::createAuthentication);
    }

    private Mono<String> validateToken(String token) {
        final String username = jwtTokenProvider.extractClaim(token, Claims::getSubject);
        return staffDetailsService.loadUserByUsernameReactive(username)
                .flatMap(userDetails -> {
                    if (jwtTokenProvider.isTokenValid(token, userDetails)) {
                        return jpaStaffSessionRepository.findByStaffId(userDetails.getStaffEntity().getId())
                                .filter(staffSession -> staffSession.getToken().equals(token))
                                .switchIfEmpty(Mono.error(new SecurityException("Session invalid. Please login again.")))
                                .thenReturn(username);
                    } else {
                        return Mono.error(new SecurityException("Invalid token. Please login again."));
                    }
                });
    }

    private Mono<Authentication> createAuthentication(String username) {
        return staffDetailsService.loadUserByUsernameReactive(username)
                .map(userDetails -> {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                     ReactiveSecurityContextHolder.withSecurityContext(ReactiveSecurityContextHolder.getContext().map(a -> {
                         a.setAuthentication(auth);
                         return a;
                     }));
                     return auth;
                });
    }
}
