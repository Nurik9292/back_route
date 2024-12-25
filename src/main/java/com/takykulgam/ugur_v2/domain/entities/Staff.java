package com.takykulgam.ugur_v2.domain.entities;

import com.takykulgam.ugur_v2.domain.exceptions.CoreException;
import com.takykulgam.ugur_v2.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.domain.values.Password;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Objects;

@Getter
public class Staff {

    private long id;
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

    public Staff(long id, String name, String rawPassword, boolean isAdmin) {
        this.id = id;
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

    public Mono<Void> checkExistName(StaffRepository staffRepository) {
        return staffRepository.existsByName(name)
                .flatMap(exists -> {
                    if (exists) return Mono.error(new CoreException("Staff member with this name already exists."));
                    return Mono.empty();
                });
    }

    public static Password createPassword(String rawPassword) {
        return new Password(rawPassword);
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
        return String.format("Staff{name='%s', isAdmin=%b}", name, isAdmin);
    }
}
