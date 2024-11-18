package com.takykulgam.ugur_v2.utils.rest.auth;

import org.springframework.web.reactive.function.client.WebClient;

public interface AuthContract {

    void apply(WebClient.RequestHeadersSpec<?> request);
}
