package com.takykulgam.ugur_v2.infrastructure.external;

import com.takykulgam.ugur_v2.applications.gateways.BusRepository;
import com.takykulgam.ugur_v2.utils.rest.ApiRequestFactory;
import com.takykulgam.ugur_v2.utils.rest.auth.AuthContract;
import com.takykulgam.ugur_v2.utils.rest.auth.BasicAuth;
import com.takykulgam.ugur_v2.utils.rest.request.ApiRequest;

import java.time.Instant;


public class BusImdataRepository implements BusRepository {

    private static final long MIN_INTERVAL_SECONDS = 60;
    private Instant lastFetchTime = Instant.now();

    private final ApiRequestFactory apiRequestFactory;

    public BusImdataRepository() {
        apiRequestFactory = new ApiRequestFactory("https://edu.ayauk.gov.tm/gps/buses/info");
    }

    @Override
    public void fetch() {
        Instant now = Instant.now();
        if(now.isAfter(lastFetchTime.plusSeconds(MIN_INTERVAL_SECONDS))) {
            System.out.println("Test");
            lastFetchTime = now;
            AuthContract auth = new BasicAuth("turkmenportal", "turkmenportal");
            ApiRequest<String> request = apiRequestFactory.createGetRequest(String.class, auth);
        //    request.execute()
        //            .doOnSuccess(System.out::println)
        //            .doOnError(WebClientResponseException.class,
        //                    e -> System.err.println("Error Response: " + e.getResponseBodyAsString()));
        }else {
            System.out.println("Skipping fetch for BusRepositoryA to respect rate limit");
        }
    }
}
