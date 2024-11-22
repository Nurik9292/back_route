package com.takykulgam.ugur_v2.infrastructure.security.admin;

import com.takykulgam.ugur_v2.infrastructure.persistnces.repositories.JpaStaffRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StaffDetailsService implements UserDetailsService {

    private final JpaStaffRepository staffRepository;

    @Autowired
    public StaffDetailsService(JpaStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public StaffDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        StaffEntity staff = staffRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        return new StaffDetails(staff, Role.ADMIN);
    }
}
