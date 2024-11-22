package com.takykulgam.ugur_v2.applications.security;

public interface CustomerPasswordEncoder {

    boolean matches(String rawPassword, String encodedPassword);

    String encode(String rawPassword);
}
