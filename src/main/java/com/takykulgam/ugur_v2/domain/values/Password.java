package com.takykulgam.ugur_v2.domain.values;

import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.domain.exceptions.CoreException;

public record Password(String value) {

    public void validate() {
        if (value == null || value.length() < 6)
            throw new CoreException("Password must be at least 6 characters long");
    }

    public void verifyPassword(String rawPassword, CustomerPasswordEncoder passwordEncoder) {
        if (rawPassword == null)
            throw new CoreException("Password cannot be null");

        if(!matches(rawPassword, passwordEncoder))
            throw new CoreException("Password verification failed");
    }


    public boolean matches(String rawPassword, CustomerPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.value);
    }


    @Override
    public String toString() {
        return "******";
    }

}