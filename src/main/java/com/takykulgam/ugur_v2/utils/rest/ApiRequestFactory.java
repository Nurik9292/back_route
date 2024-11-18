package com.takykulgam.ugur_v2.utils.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takykulgam.ugur_v2.utils.rest.auth.AuthContract;
import com.takykulgam.ugur_v2.utils.rest.request.ApiRequest;
import com.takykulgam.ugur_v2.utils.rest.request.GetRequest;
import com.takykulgam.ugur_v2.utils.rest.request.PostRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

public class ApiRequestFactory {

   private static ApiRequestFactory instance;
   private final WebClient webClient;

    public ApiRequestFactory(String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        this.webClient = WebClient
                .builder()
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(conf -> {
                            conf.defaultCodecs().maxInMemorySize(10 * 1024 * 1024);
                            conf.defaultCodecs().jackson2JsonDecoder(
                                    new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON, MediaType.valueOf("text/json"))
                            );
                            conf.defaultCodecs().jackson2JsonEncoder(
                                    new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)
                            );
                        })
                        .build())
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(createHttpClient()))
                .build();
    }

    public <T> ApiRequest<T> createGetRequest(Class<T> responseType, AuthContract auth) {
        return new GetRequest<>(webClient, responseType, auth);
    }

    public <T> ApiRequest<T> createPostRequest(Object requestBody, Class<T> responseType, AuthContract auth) {
        return new PostRequest<>(webClient, requestBody, responseType, auth);
    }

    public <T> ApiRequest<T> createPostRequestNonBody(Class<T> responseType, AuthContract auth) {
        return new PostRequest<>(webClient, responseType, auth);
    }

    private HttpClient createHttpClient() {
        return HttpClient.create().resolver(spec -> spec.queryTimeout(Duration.ofSeconds(40)));
    }

}
