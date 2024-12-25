package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import com.takykulgam.ugur_v2.applications.boundaries.input.InputPaginate;
import com.takykulgam.ugur_v2.applications.usecase.banner.BannerCreateUseCase;
import com.takykulgam.ugur_v2.applications.usecase.banner.BannerDeleteUseCase;
import com.takykulgam.ugur_v2.applications.usecase.banner.RetrieveAllBannerUseCase;
import com.takykulgam.ugur_v2.applications.boundaries.output.UseCaseExecutor;
import com.takykulgam.ugur_v2.infrastructure.storage.FileSystemStorage;
import com.takykulgam.ugur_v2.interfaces.dto.PageResult;
import com.takykulgam.ugur_v2.interfaces.dto.banner.CreateBanner;
import com.takykulgam.ugur_v2.applications.boundaries.output.OutputBanner;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/admin/banners")
public class BannerController {

    private static final String PATH_BANNER = "banners/";

    private final BannerCreateUseCase bannerCreateUseCase;
    private final RetrieveAllBannerUseCase retrieveAllBannerUseCase;
    private final BannerDeleteUseCase bannerDeleteUseCase;
    private final UseCaseExecutor useCaseExecutor;
    private final FileSystemStorage fileSystemStorage;

    @Autowired
    public BannerController(BannerCreateUseCase bannerCreateUseCase,
                            RetrieveAllBannerUseCase retrieveAllBannerUseCase,
                            BannerDeleteUseCase bannerDeleteUseCase,
                            UseCaseExecutor useCaseExecutor,
                            FileSystemStorage fileSystemStorage) {
        this.bannerCreateUseCase = bannerCreateUseCase;
        this.retrieveAllBannerUseCase = retrieveAllBannerUseCase;
        this.bannerDeleteUseCase = bannerDeleteUseCase;
        this.useCaseExecutor = useCaseExecutor;
        this.fileSystemStorage = fileSystemStorage;
    }

    @GetMapping
    public Mono<PageResult<OutputBanner>> getBanners(
            @RequestParam(name = "order") String order,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return useCaseExecutor.execute(
                retrieveAllBannerUseCase,
                Mono.just(new InputPaginate(page, size, sort, order)),
                RetrieveAllBannerUseCase.Output::result
        );
    }

    @PostMapping
    public Mono<OutputBanner> createBanner(@RequestBody CreateBanner createBanner) {
        return useCaseExecutor.execute(
                bannerCreateUseCase,
                createBanner.toInput(),
                BannerCreateUseCase.Output::result
        );
    }

    @DeleteMapping("/{imageName}")
    public Mono<String>  deleteBanner(@PathVariable("imageName") String imageName) {
        return useCaseExecutor.execute(
                bannerDeleteUseCase,
                Mono.just(imageName),
                BannerDeleteUseCase.Output::message
        );
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<Resource>> getImage(@PathVariable String imageName) {
        return fileSystemStorage
                .loadAsResource(PATH_BANNER + imageName)
                .map(path -> ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(path))
                .onErrorResume(e -> {
                    log.error("Ошибка при загрузке баннера: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.notFound().build());
                });
    }

}
