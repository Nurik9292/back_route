package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.gateways.ImageRepository;
import com.takykulgam.ugur_v2.applications.gateways.StaffRepository;
import com.takykulgam.ugur_v2.applications.iteractor.image.SaveImageService;
import com.takykulgam.ugur_v2.applications.iteractor.staff.*;
import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.interfaces.presenters.SimpleExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseImplementer {

    @Bean
    public AuthStaffLoginCase authStaffLoginCase(
            GetStaffByNameUseCase getUserByNameUseCase,
            CustomerPasswordEncoder customerPasswordEncoder,
            CustomAuthentication customAuthentication) {
        return new AuthStaffLoginCase(getUserByNameUseCase, customerPasswordEncoder, customAuthentication);
    }

    @Bean
    public RetrieveAllStaffCase retrieveAllStaffCase(StaffRepository staffRepository) {
        return new RetrieveAllStaffCase(staffRepository);
    }

    @Bean
    public GetStaffByIdUseCase getStaffByIdUseCase(StaffRepository staffRepository) {
        return new GetStaffByIdUseCase(staffRepository);
    }

    @Bean
    public GetStaffByNameUseCase getUserByNameUseCase(StaffRepository staffRepository) {
        return new GetStaffByNameUseCase(staffRepository);
    }

    @Bean
    public StaffCreateCase staffCreateCase(StaffRepository staffRepository, ExistStaffUseCase existStaffUseCase) {
        return new StaffCreateCase(staffRepository, existStaffUseCase);
    }

    @Bean
    public StaffUpdateCase staffUpdateCase(StaffRepository staffRepository, GetStaffByIdUseCase getStaffByIdUseCase) {
        return new StaffUpdateCase(getStaffByIdUseCase, staffRepository);
    }

    @Bean
    public StaffMeUseCase staffMeUseCase(StaffRepository staffRepository, SaveImageService saveImageService) {
        return new StaffMeUseCase(staffRepository, saveImageService);
    }

    @Bean
    public StaffDeleteCase staffDeleteCase(StaffRepository staffRepository) {
        return new StaffDeleteCase(staffRepository);
    }

    @Bean
    public ExistStaffUseCase existStaffUseCase(StaffRepository staffRepository) {
        return new ExistStaffUseCase(staffRepository);
    }

    @Bean
    public SaveImageService saveImageService(ImageRepository imageRepository) {
        return new SaveImageService(imageRepository);
    }

    @Bean
    public UseCaseExecutor useCaseExecutor() {
        return new SimpleExecutor();
    }
}
