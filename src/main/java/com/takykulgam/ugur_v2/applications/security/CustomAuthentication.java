package com.takykulgam.ugur_v2.applications.security;


import reactor.core.publisher.Mono;

public interface CustomAuthentication {

    Mono<String> authenticate(String username, String password);
}
