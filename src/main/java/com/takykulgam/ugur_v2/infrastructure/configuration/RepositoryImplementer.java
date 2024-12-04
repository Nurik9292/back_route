package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.gateways.BusRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusAtLogistikRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusImdataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryImplementer {

    @Bean
    public BusRepository busImdataRepository() {
        return new BusImdataRepository();
    }

    @Bean
    public BusRepository busAtLogistikRepository() {
        return new BusAtLogistikRepository();
    }

}
