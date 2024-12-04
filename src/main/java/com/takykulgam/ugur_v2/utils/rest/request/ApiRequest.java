package com.takykulgam.ugur_v2.utils.rest.request;

import com.takykulgam.ugur_v2.utils.rest.auth.AuthContract;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public abstract class ApiRequest<T> implements ApiContract<T> {

    protected final WebClient webClient;
    protected Class<T> responseType;
    protected ParameterizedTypeReference<T> responseTypeRef;
    protected final AuthContract auth;

    protected ApiRequest(WebClient webClient, Class<T> responseType, AuthContract auth) {
        this.webClient = webClient;
        this.responseType = responseType;
        this.auth = auth;
    }

    protected ApiRequest(WebClient webClient, ParameterizedTypeReference<T> responseType, AuthContract auth) {
        this.webClient = webClient;
        this.responseTypeRef = responseType;
        this.auth = auth;
    }

    @Override
    public abstract Flux<T> execute();

    protected void applyAuthAndPrepareRequest(WebClient.RequestHeadersSpec<?> requestSpec) {
        auth.apply(requestSpec);
    }
}
