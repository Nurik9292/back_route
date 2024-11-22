package com.takykulgam.ugur_v2.infrastructure.database;

import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final JpaStaffRepository jpaStaffRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(JpaStaffRepository jpaStaffRepository, PasswordEncoder passwordEncoder) {
        this.jpaStaffRepository = jpaStaffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(jpaStaffRepository.count() == 0) {
            StaffEntity staff = new StaffEntity("Test", passwordEncoder.encode("123456789"), true);
            staff.setUpdatedAt(new Date());
            staff.setCreatedAt(new Date());
            jpaStaffRepository.save(staff);
        }
    }
}
