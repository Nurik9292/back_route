package com.takykulgam.ugur_v2.infrastructure.database.migrations;

import com.takykulgam.ugur_v2.infrastructure.database.persistnces.repositories.R2dbcStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.database.persistnces.entities.StaffEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final R2dbcStaffRepository jpaStaffRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(R2dbcStaffRepository jpaStaffRepository, PasswordEncoder passwordEncoder) {
        this.jpaStaffRepository = jpaStaffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        jpaStaffRepository.count()
                .flatMap(count -> {
//                    if (count == 0) {
//                        StaffEntity staff = new StaffEntity("Test", passwordEncoder.encode("123456789"), true);
//                        staff.setUpdatedAt(LocalDateTime.now());
//                        staff.setCreatedAt(LocalDateTime.now());
//                        return jpaStaffRepository.save(staff);
//                    }
                    if(count > 0) {
                       return jpaStaffRepository
                                .findByName("Test")
                                .flatMap(staffEntity -> {
                                    staffEntity.setPassword(passwordEncoder.encode("123456789"));
                                    return jpaStaffRepository.save(staffEntity);
                                });
                    }
                    return Mono.empty();
                })
                .subscribe();
    }
}
