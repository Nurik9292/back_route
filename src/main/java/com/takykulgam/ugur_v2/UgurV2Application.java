package com.takykulgam.ugur_v2;

import com.takykulgam.ugur_v2.applications.iteractor.*;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffDeleteCase;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffUpdateCase;
import com.takykulgam.ugur_v2.interfaces.gateway.StaffRepositoryImpl;
import com.takykulgam.ugur_v2.interfaces.presenters.StaffAllPresenter;
import com.takykulgam.ugur_v2.interfaces.presenters.StaffAuthPresenter;
import com.takykulgam.ugur_v2.interfaces.presenters.StaffDeletePresenter;
import com.takykulgam.ugur_v2.interfaces.presenters.StaffPresenter;
import com.takykulgam.ugur_v2.applications.processors.EntityProcessor;
import com.takykulgam.ugur_v2.interfaces.processors.StaffEntityProcessor;
import com.takykulgam.ugur_v2.interfaces.viewmodels.ListStaffViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.Response;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffAuthViewModel;
import com.takykulgam.ugur_v2.interfaces.viewmodels.StaffViewModel;
import com.takykulgam.ugur_v2.core.boundaries.dto.OutputStaff;
import com.takykulgam.ugur_v2.core.boundaries.input.auth.AuthStaffLoginCase;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.RetrieveAllStaffCase;
import com.takykulgam.ugur_v2.core.boundaries.input.staff.StaffCreateCase;
import com.takykulgam.ugur_v2.core.boundaries.output.Presenter;
import com.takykulgam.ugur_v2.applications.gateways.BusRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusAtLogistikRepository;
import com.takykulgam.ugur_v2.infrastructure.external.BusImdataRepository;
import com.takykulgam.ugur_v2.infrastructure.persistnces.entities.StaffEntity;
import com.takykulgam.ugur_v2.infrastructure.security.PasswordEncoderImpl;
import com.takykulgam.ugur_v2.infrastructure.security.admin.CustomAuthenticationImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class UgurV2Application {

    public static void main(String[] args) {
        SpringApplication.run(UgurV2Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
    EntityProcessor<StaffEntity> staffEntityProcessor() {
        return new StaffEntityProcessor(passwordEncoder());
    }

    @Bean
    public Presenter<OutputStaff, Mono<Response<StaffViewModel>>> staffPresenter() {
        return new StaffPresenter();
    }

    @Bean
    public Presenter<Flux<OutputStaff>, Mono<Response<ListStaffViewModel>>> staffAllPresenter() {
        return new StaffAllPresenter();
    }

    @Bean
    public Presenter<String, Mono<Response<StaffAuthViewModel>>> authLoginPresenter() {
        return new StaffAuthPresenter();
    }

    @Bean
    public Presenter<String, Mono<Response<String>>> staffDeletePresenter() {
        return new StaffDeletePresenter();
    }

    @Bean
    public StaffCreateCase staffCreateCase(StaffRepositoryImpl staffRepositoryImpl) {
        return new StaffCreateCaseImpl(staffRepositoryImpl, staffPresenter());
    }

    @Bean
    public StaffUpdateCase staffUpdateCase(StaffRepositoryImpl staffRepositoryImpl) {
        return new StaffUpdateCaseImpl(staffRepositoryImpl, staffPresenter());
    }

    @Bean
    public RetrieveAllStaffCase retrieveAllStaffCase(StaffRepositoryImpl staffRepositoryImpl) {
        return new RetrieveAllStaffCaseImpl(staffRepositoryImpl, staffAllPresenter());
    }

    @Bean
    public StaffDeleteCase staffDelete(StaffRepositoryImpl staffRepositoryImpl) {
        return new StaffDeleteCaseImpl(staffRepositoryImpl, staffDeletePresenter());
    }

    @Bean
    public AuthStaffLoginCase authStaffLoginCase(StaffRepositoryImpl staffRepositoryImpl,
                                                 PasswordEncoderImpl passwordEncoderImpl,
                                                 CustomAuthenticationImpl customAuthenticationImpl) {
        return new AuthStaffLoginCaseImpl(
                staffRepositoryImpl,
                passwordEncoderImpl,
                customAuthenticationImpl,
                authLoginPresenter());
    }


}
