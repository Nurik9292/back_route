package com.takykulgam.ugur_v2.core.domain.entities;

import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.domain.exceptions.CoreException;
import lombok.Getter;

import java.util.Base64;
import java.util.Objects;

@Getter
public class Staff {

    private final String name;
    private final boolean isAdmin;
    private final Password password;
    private String avatar;

    private static final int MAX_AVATAR_SIZE_BYTES = 1024 * 1024;

    public Staff(String name) {
        this(name, null, false);
    }

    public Staff(String name, boolean isAdmin) {
        this(name, null, isAdmin);
    }

    public Staff(String name, String rawPassword, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = new Password(rawPassword);
    }

    public Staff(String name, String rawPassword, String avatar, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.avatar = avatar;
        this.password = new Password(rawPassword);
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    public void validateName() {
        if (name == null || name.length() < 3) {
            throw new CoreException("Name must be at least 3 characters long");
        }
    }

    public void validatePassword() {
        password.validate();
    }

    public void validateAvatar() {
        if (avatar == null || avatar.isBlank()) {
            throw new CoreException("Avatar cannot be null or empty");
        }

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(avatar);
            if (decodedBytes.length > MAX_AVATAR_SIZE_BYTES) {
                throw new CoreException("Avatar size exceeds the maximum limit of 1MB");
            }
        } catch (IllegalArgumentException e) {
            throw new CoreException("Invalid Base64 format for avatar");
        }
    }

    public static Password createPassword(String rawPassword) {
        return new Password(rawPassword);
    }

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

        public void matches() {

        }

            @Override
            public String toString() {
                return "******";
            }

        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return isAdmin == staff.isAdmin && Objects.equals(name, staff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isAdmin);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
