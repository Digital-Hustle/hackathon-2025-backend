package rnd.sueta.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.controller.PlaceController;
import rnd.sueta.dto.PhotoMetaDto;
import rnd.sueta.dto.PlaceDto;
import rnd.sueta.dto.ReviewDto;
import rnd.sueta.dto.params.PaginationFilter;
import rnd.sueta.dto.request.CreatePlaceRq;
import rnd.sueta.dto.request.CreateReviewRq;
import rnd.sueta.dto.request.UpdatePlaceRq;
import rnd.sueta.dto.request.UpdateReviewRq;
import rnd.sueta.dto.response.GetPhotosMetaWithUrlRs;
import rnd.sueta.dto.response.GetPlacesRs;
import rnd.sueta.dto.response.GetReviewsRs;
import rnd.sueta.mapper.PhotoMapper;
import rnd.sueta.mapper.PlaceMapper;
import rnd.sueta.mapper.ReviewMapper;
import rnd.sueta.model.PhotoWithUrl;
import rnd.sueta.model.PlaceWithCoordinates;
import rnd.sueta.model.entity.PhotoMeta;
import rnd.sueta.model.entity.Review;
import rnd.sueta.service.business.CacheProvider;
import rnd.sueta.service.business.PhotoManager;
import rnd.sueta.service.business.ReviewRegistrator;
import rnd.sueta.service.entity.PlaceService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements PlaceController {

    private final PlaceService placeService;
    private final PhotoManager placePhotoManager;
    private final ReviewRegistrator placeReviewRegistrator;
    private final CacheProvider<PlaceWithCoordinates> cacheProvider;

    private final PlaceMapper placeMapper;
    private final PhotoMapper photoMapper;
    private final ReviewMapper reviewMapper;

    @Override
    public GetPlacesRs getAll(PaginationFilter paginationFilter) {
        Page<PlaceWithCoordinates> places = placeService.getAll(paginationFilter.page(), paginationFilter.size());

        return GetPlacesRs.builder()
                .places(placeMapper.convert(places))
                .build();
    }

    @Override
    public GetPlacesRs getTop(PaginationFilter paginationFilter) {
        Page<PlaceWithCoordinates> places = cacheProvider.getTop(paginationFilter.page(), paginationFilter.size());

        return GetPlacesRs.builder()
                .places(placeMapper.convert(places))
                .build();
    }

    @Override
    public GetPhotosMetaWithUrlRs getAllByOwnerId(UUID id, PaginationFilter paginationFilter) {
        Page<PhotoWithUrl> photosMetaWithUrl = placePhotoManager.getAllByOwnerId(
                id, paginationFilter.page(), paginationFilter.size()
        );

        return GetPhotosMetaWithUrlRs.builder()
                .photosMetaWithUrl(photoMapper.convert(photosMetaWithUrl))
                .build();
    }

    @Override
    public GetReviewsRs getAllReviewsByOwnerId(UUID id, PaginationFilter paginationFilter) {
        Page<Review> reviews = placeReviewRegistrator.getAllByOwnerId(
                id, paginationFilter.page(), paginationFilter.size()
        );

        return GetReviewsRs.builder()
                .reviews(reviewMapper.convert(reviews))
                .build();
    }

    @Override
    public PlaceDto getById(UUID id) {
        return placeMapper.convert(cacheProvider.getById(id));
    }

    @Override
    public ReviewDto getReviewById(UUID reviewId) {
        Review review = placeReviewRegistrator.getById(reviewId);

        return reviewMapper.convert(review);
    }

    @Override
    public PlaceDto create(CreatePlaceRq createPlaceRq) {
        PlaceWithCoordinates place = placeMapper.convert(createPlaceRq);
        place = placeService.create(place);

        return placeMapper.convert(place);
    }

    @Override
    public PhotoMetaDto uploadPhoto(UUID id, MultipartFile photo) {
        PhotoMeta photoMeta = placePhotoManager.createPhoto(id, photo);

        return photoMapper.convert(photoMeta);
    }

    @Override
    public ReviewDto createReview(UUID id, CreateReviewRq createReviewRq) {
        Review review = reviewMapper.convert(createReviewRq);

        return reviewMapper.convert(placeReviewRegistrator.register(id, review));
    }

    @Override
    public PlaceDto update(UUID id, UpdatePlaceRq updatePlaceRq) {
        PlaceWithCoordinates place = placeMapper.convert(id, updatePlaceRq);

        return placeMapper.convert(cacheProvider.update(place));
    }

    @Override
    public ReviewDto updateReview(UUID id, UUID reviewId, UpdateReviewRq updatereviewRq) {
        Review review = reviewMapper.convert(reviewId, updatereviewRq);

        return reviewMapper.convert(placeReviewRegistrator.update(id, review));
    }

    @Override
    public void delete(UUID id) {
        cacheProvider.delete(id);
    }

    @Override
    public void deletePhoto(UUID photoId) {
        placePhotoManager.deletePhoto(photoId);
    }

    @Override
    public void deleteReview(UUID id, UUID reviewId) {
        placeReviewRegistrator.delete(id, reviewId);
    }
}
