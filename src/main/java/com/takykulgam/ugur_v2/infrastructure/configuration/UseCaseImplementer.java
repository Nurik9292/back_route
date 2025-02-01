package com.takykulgam.ugur_v2.infrastructure.configuration;

import com.takykulgam.ugur_v2.applications.usecase.banner.BannerCreateUseCase;
import com.takykulgam.ugur_v2.applications.usecase.banner.BannerDeleteUseCase;
import com.takykulgam.ugur_v2.applications.usecase.banner.BannerGetByIdUseCase;
import com.takykulgam.ugur_v2.applications.usecase.banner.RetrieveAllBannerUseCase;
import com.takykulgam.ugur_v2.applications.usecase.city.*;
import com.takykulgam.ugur_v2.applications.usecase.geo.PointCreateUseCase;
import com.takykulgam.ugur_v2.applications.usecase.image.DeleteImageService;
import com.takykulgam.ugur_v2.applications.usecase.route.RouteCreateUseCase;
import com.takykulgam.ugur_v2.applications.usecase.stop.*;
import com.takykulgam.ugur_v2.applications.usecase.image.SaveImageService;
import com.takykulgam.ugur_v2.applications.usecase.staff.*;
import com.takykulgam.ugur_v2.applications.security.CustomAuthentication;
import com.takykulgam.ugur_v2.applications.security.CustomerPasswordEncoder;
import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.domain.gateways.*;
import com.takykulgam.ugur_v2.interfaces.presenters.SimpleExecutor;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseImplementer {

    @Bean
    public AuthStaffLoginCase authStaffLoginCase(
            StaffGetByNameUseCase getUserByNameUseCase,
            CustomerPasswordEncoder customerPasswordEncoder,
            CustomAuthentication customAuthentication) {
        return new AuthStaffLoginCase(getUserByNameUseCase, customerPasswordEncoder, customAuthentication);
    }

    @Bean
    public RetrieveAllStaffCase retrieveAllStaffCase(StaffRepository staffRepository) {
        return new RetrieveAllStaffCase(staffRepository);
    }

    @Bean
    public StaffGetByIdUseCase getStaffByIdUseCase(StaffRepository staffRepository) {
        return new StaffGetByIdUseCase(staffRepository);
    }

    @Bean
    public StaffGetByNameUseCase getUserByNameUseCase(StaffRepository staffRepository) {
        return new StaffGetByNameUseCase(staffRepository);
    }

    @Bean
    public StaffCreateCase staffCreateCase(StaffRepository staffRepository) {
        return new StaffCreateCase(staffRepository);
    }

    @Bean
    public StaffUpdateCase staffUpdateCase(StaffRepository staffRepository) {
        return new StaffUpdateCase(staffRepository);
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
                                           StaffGetByIdUseCase getStaffByIdUseCase) {
        return new StaffDeleteCase(staffRepository, deleteImageService, getStaffByIdUseCase);
    }

    @Bean
    public StaffExistUseCase existStaffUseCase(StaffRepository staffRepository) {
        return new StaffExistUseCase(staffRepository);
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
    public FetchAllCityUseCase fetchAllCityUseCase(CityRepository cityRepository) {
        return new FetchAllCityUseCase(cityRepository);
    }

    @Bean
    public CityCreateUseCase cityCreateUseCase(CityRepository cityRepository) {
        return new CityCreateUseCase(cityRepository);
    }

    @Bean
    public CityUpdateUseCase cityUpdateUseCase(CityRepository cityRepository, CityGetByIdUseCase getCityByIdUseCase) {
        return new CityUpdateUseCase(cityRepository, getCityByIdUseCase);
    }
    @Bean
    public CityGetByIdUseCase getCityByIdUseCase(CityRepository cityRepository) {
        return new CityGetByIdUseCase(cityRepository);
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
    public BannerGetByIdUseCase getBannerByIdUseCase(BannerRepository bannerRepository) {
        return new BannerGetByIdUseCase(bannerRepository);
    }

    @Bean
    public StopGetByIdUseCase getStopByIdUseCase(StopRepository stopRepository) {
        return new StopGetByIdUseCase(stopRepository);
    }

    @Bean
    public StopCreateUseCase createStopUseCase(StopRepository stopRepository) {
        return new StopCreateUseCase(stopRepository);
    }

    @Bean
    public StopUpdateUseCase updateStopUseCase(StopRepository stopRepository, StopGetByIdUseCase getStopByIdUseCase) {
        return new StopUpdateUseCase(stopRepository, getStopByIdUseCase);
    }

    @Bean
    public RetrieveAllStopCase retrieveAllStopUseCase(StopRepository stopRepository) {
        return new RetrieveAllStopCase(stopRepository);
    }

    @Bean
    public StopDeleteUseCase stopDeleteUseCase(StopRepository stopRepository) {
        return new StopDeleteUseCase(stopRepository);
    }

    @Bean
    public FetchAllStopUseCase fetchAllStopUseCase(StopRepository stopRepository) {
        return new FetchAllStopUseCase(stopRepository);
    }

    @Bean
    public UseCaseExecutor useCaseExecutor() {
        return new SimpleExecutor();
    }

    @Bean
    public DeleteImageService deleteImageService(ImageRepository imageRepository) {
        return new DeleteImageService(imageRepository);
    }

    @Bean
    public PointCreateUseCase pointCreateUseCase(GeometryFactory geometryFactory) {
        return new PointCreateUseCase(geometryFactory);
    }

    @Bean
    public RouteCreateUseCase routeCreateUseCase(RouteRepository routeRepository,
                                                 RouteDirectionRepository routeDirectionRepository) {
        return new RouteCreateUseCase(routeRepository, routeDirectionRepository);
    }
}
