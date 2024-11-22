package com.takykulgam.ugur_v2.infrastructure.external;

import com.takykulgam.ugur_v2.applications.gateways.BusRepository;
import com.takykulgam.ugur_v2.infrastructure.external.dto.BusAtLogistikaDto;
import com.takykulgam.ugur_v2.utils.rest.ApiRequestFactory;
import com.takykulgam.ugur_v2.utils.rest.auth.AuthContract;
import com.takykulgam.ugur_v2.utils.rest.auth.BasicAuth;
import com.takykulgam.ugur_v2.utils.rest.request.ApiRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class BusAtLogistikRepository implements BusRepository {

    private final ApiRequestFactory apiRequestFactory;


    public BusAtLogistikRepository() {
        this.apiRequestFactory = new ApiRequestFactory("https://atlogistika.com/api/api.php?cmd=list");
    }

    @Override
    public void fetch() {
        System.out.println("At logistika API");
        AuthContract auth = new BasicAuth("turkmenavtoulag", "Awto996");
        ApiRequest<BusAtLogistikaDto> request = apiRequestFactory.createGetRequest(BusAtLogistikaDto.class, auth);
        request.execute()
                .doOnNext(System.out::println)
                .doOnError(WebClientResponseException.class,
                    e -> System.err.println("Error Response: " + e.getResponseBodyAsString())).blockFirst();
    }
}
