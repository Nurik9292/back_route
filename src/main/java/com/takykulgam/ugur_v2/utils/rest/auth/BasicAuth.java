package com.takykulgam.ugur_v2.utils.rest.auth;

import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuth implements AuthContract {

    private final String username;
    private final String password;

    public BasicAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void apply(WebClient.RequestHeadersSpec<?> request) {
        String basicAuth = "Basic " +
                Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        request.header("Authorization", basicAuth);
    }


}
