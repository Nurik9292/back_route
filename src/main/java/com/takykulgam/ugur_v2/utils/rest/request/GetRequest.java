package com.takykulgam.ugur_v2.utils.rest.request;

import com.takykulgam.ugur_v2.utils.rest.auth.AuthContract;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class GetRequest<T> extends ApiRequest<T> {

    public GetRequest(WebClient webClient, Class<T> responseType, AuthContract auth) {
      super(webClient, responseType, auth);
    }

    public GetRequest(WebClient webClient, ParameterizedTypeReference<T> responseType, AuthContract auth) {
        super(webClient, responseType, auth);
    }

    @Override
    public Flux<T> execute() {
        WebClient.RequestHeadersSpec<?> request = webClient.get();
        applyAuthAndPrepareRequest(request);
        return request.retrieve().bodyToFlux(responseType);
    }
}
