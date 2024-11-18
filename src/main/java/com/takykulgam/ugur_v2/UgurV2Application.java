package com.takykulgam.ugur_v2;

import com.takykulgam.ugur_v2.applications.data_access.bus.BusRepository;
import com.takykulgam.ugur_v2.applications.usecase.input.FetchBusData;
import com.takykulgam.ugur_v2.applications.usecase.iteractor.FetchBusDataImpl;
import com.takykulgam.ugur_v2.infrastructure.external.BusAtLogistikRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusImdataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class UgurV2Application {

    public static void main(String[] args) {
        SpringApplication.run(UgurV2Application.class, args);
    }

    @Bean
    public BusRepository busImdataRepository() {
        return new BusImdataRepository();
    }

    @Bean
    public BusRepository busAtLogistikRepository() {
        return new BusAtLogistikRepository();
    }

    @Bean
    public FetchBusData fetchBusData(List<BusRepository> busRepositories) {
        return new FetchBusDataImpl(busRepositories);
    }

}
