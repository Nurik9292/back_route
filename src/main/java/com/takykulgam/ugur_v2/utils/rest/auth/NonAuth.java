package com.takykulgam.ugur_v2.utils.rest.auth;

import org.springframework.web.reactive.function.client.WebClient;

public class NonAuth implements AuthContract {

    @Override
    public void apply(WebClient.RequestHeadersSpec<?> request) {

    }
}
