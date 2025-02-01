package com.takykulgam.ugur_v2.infrastructure.security.admin;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcStaffSessionRepository;
import com.takykulgam.ugur_v2.infrastructure.security.JwtTokenProviderImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AdminAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProviderImpl jwtTokenProvider;
    private final StaffDetailsService staffDetailsService;
    private final R2dbcStaffSessionRepository staffSessionRepository;

    @Autowired
    public AdminAuthenticationManager(JwtTokenProviderImpl jwtTokenProvider,
                                      StaffDetailsService staffDetailsService,
                                      R2dbcStaffSessionRepository staffSessionRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.staffDetailsService = staffDetailsService;
        this.staffSessionRepository = staffSessionRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        return Mono.defer(() -> validateToken(token))
                .flatMap(this::createAuthentication)
                .onErrorResume(e -> Mono.error(new SecurityException("Authentication failed: " + e.getMessage(), e)));
    }

    private Mono<String> validateToken(String token) {
        return Mono.justOrEmpty(jwtTokenProvider.extractClaim(token, Claims::getSubject))
                .switchIfEmpty(Mono.error(new SecurityException("Token does not contain a valid subject.")))
                .flatMap(username -> staffDetailsService.loadUserByUsernameReactive(username)
                        .flatMap(userDetails -> {
                            if (!jwtTokenProvider.isTokenValid(token, userDetails)) {
                                return Mono.error(new SecurityException("Invalid token."));
                            }
                            return staffSessionRepository.findByStaffId(userDetails.getStaffEntity().getId())
                                    .filter(session -> session.getToken().equals(token))
                                    .switchIfEmpty(Mono.error(new SecurityException("Session invalid.")))
                                    .thenReturn(username);
                        }));
    }

    private Mono<Authentication> createAuthentication(String username) {
        return staffDetailsService.loadUserByUsernameReactive(username)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()));
    }
}
