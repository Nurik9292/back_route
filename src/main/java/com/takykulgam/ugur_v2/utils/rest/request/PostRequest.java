package com.takykulgam.ugur_v2.utils.rest.request;

import com.takykulgam.ugur_v2.utils.rest.auth.AuthContract;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class PostRequest<T> extends ApiRequest<T> {

    private final Object requestBody;

    public PostRequest(WebClient webClient, Object requestBody, Class<T> responseType, AuthContract auth) {
        super(webClient, responseType, auth);
        this.requestBody = requestBody;
    }

    public PostRequest(WebClient webClient, Class<T> responseType, AuthContract auth) {
        super(webClient, responseType, auth);
        this.requestBody = null;
    }

    @Override
    public Flux<T> execute() {
        WebClient.RequestHeadersSpec<?> request = requestBody == null ? webClient.post() : webClient.post().bodyValue(requestBody);
        applyAuthAndPrepareRequest(request);
        return request.retrieve().bodyToFlux(responseType);
    }


}
