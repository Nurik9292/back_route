package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.iteractor.banner.BannerCreateUseCase;
import com.takykulgam.ugur_v2.applications.iteractor.banner.BannerDeleteUseCase;
import com.takykulgam.ugur_v2.applications.iteractor.banner.GetBannerByIdUseCase;
import com.takykulgam.ugur_v2.applications.iteractor.banner.RetrieveAllBannerUseCase;
import com.takykulgam.ugur_v2.applications.iteractor.city.*;
import com.takykulgam.ugur_v2.applications.iteractor.image.DeleteImageService;
import com.takykulgam.ugur_v2.core.domain.gateways.BannerRepository;
import com.takykulgam.ugur_v2.core.domain.gateways.CityRepository;
import com.takykulgam.ugur_v2.core.domain.gateways.ImageRepository;
import com.takykulgam.ugur_v2.applications.iteractor.image.SaveImageService;
import com.takykulgam.ugur_v2.applications.iteractor.staff.*;
import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.core.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.core.domain.gateways.StaffRepository;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystem;
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
    public StaffMeUseCase staffMeUseCase(StaffRepository staffRepository,
                                         SaveImageService saveImageService,
                                         CustomerPasswordEncoder customerPasswordEncoder) {
        return new StaffMeUseCase(staffRepository, saveImageService, customerPasswordEncoder);
    }

    @Bean
    public StaffDeleteCase staffDeleteCase(StaffRepository staffRepository,
                                           DeleteImageService deleteImageService,
                                           GetStaffByIdUseCase getStaffByIdUseCase) {
        return new StaffDeleteCase(staffRepository, deleteImageService, getStaffByIdUseCase);
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
    public RetrieveAllCityUseCase retrieveAllCityUseCase(CityRepository cityRepository) {
        return new RetrieveAllCityUseCase(cityRepository);
    }

    @Bean
    public CityCreateUseCase cityCreateUseCase(CityRepository cityRepository) {
        return new CityCreateUseCase(cityRepository);
    }

    @Bean
    public CityUpdateUseCase cityUpdateUseCase(CityRepository cityRepository, GetCityByIdUseCase getCityByIdUseCase) {
        return new CityUpdateUseCase(cityRepository, getCityByIdUseCase);
    }
    @Bean
    public GetCityByIdUseCase getCityByIdUseCase(CityRepository cityRepository) {
        return new GetCityByIdUseCase(cityRepository);
    }

    @Bean
    public CityDeleteUseCase cityDeleteUseCase(CityRepository cityRepository) {
        return new CityDeleteUseCase(cityRepository);
    }

    @Bean
    public BannerCreateUseCase bannerCreateUseCase(BannerRepository bannerRepository, SaveImageService saveImageService) {
        return new BannerCreateUseCase(bannerRepository, saveImageService);
    }

    @Bean
    public RetrieveAllBannerUseCase retrieveAllBannerUseCase(BannerRepository bannerRepository) {
        return new RetrieveAllBannerUseCase(bannerRepository);
    }

    @Bean
    public BannerDeleteUseCase bannerDeleteUseCase(BannerRepository bannerRepository,
                                                   DeleteImageService deleteImageService) {
        return new BannerDeleteUseCase(bannerRepository, deleteImageService);
    }

    @Bean
    public GetBannerByIdUseCase getBannerByIdUseCase(BannerRepository bannerRepository) {
        return new GetBannerByIdUseCase(bannerRepository);
    }

    @Bean
    public UseCaseExecutor useCaseExecutor() {
        return new SimpleExecutor();
    }

    @Bean
    public DeleteImageService deleteImageService(ImageRepository imageRepository) {
        return new DeleteImageService(imageRepository);
    }
}
