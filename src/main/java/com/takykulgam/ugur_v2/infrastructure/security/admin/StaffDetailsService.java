package com.takykulgam.ugur_v2.infrastructure.security.admin;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StaffDetailsService implements ReactiveUserDetailsService {

    private final R2dbcStaffRepository staffRepository;

    @Autowired
    public StaffDetailsService(R2dbcStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return staffRepository.findByName(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)))
                .map(this::mapToStaffDetails);
    }

    public Mono<StaffDetails> loadUserByUsernameReactive(String username) {
        return staffRepository.findByName(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)))
                .map(this::mapToStaffDetails);
    }

    private StaffDetails mapToStaffDetails(StaffEntity staffEntity) {
        return new StaffDetails(staffEntity, Role.ADMIN);
    }
}
