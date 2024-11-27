package com.takykulgam.ugur_v2.interfaces.processors;

import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

public class StaffEntityProcessor implements EntityProcessor<StaffEntity> {

    private PasswordEncoder passwordEncoder;

    public StaffEntityProcessor(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void preprocessBeforeSave(StaffEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }

    @Override
    public void preprocessBeforeUpdate(StaffEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
