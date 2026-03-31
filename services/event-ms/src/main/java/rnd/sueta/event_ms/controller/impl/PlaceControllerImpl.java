package rnd.sueta.event_ms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.controller.PlaceController;
import rnd.sueta.event_ms.dto.PhotoMetaDto;
import rnd.sueta.event_ms.dto.PlaceDto;
import rnd.sueta.event_ms.dto.ReviewDto;
import rnd.sueta.event_ms.dto.params.PaginationFilter;
import rnd.sueta.event_ms.dto.request.CreatePlaceRq;
import rnd.sueta.event_ms.dto.request.CreateReviewRq;
import rnd.sueta.event_ms.dto.request.UpdatePlaceRq;
import rnd.sueta.event_ms.dto.request.UpdateReviewRq;
import rnd.sueta.event_ms.dto.response.GetPhotosMetaWithUrlRs;
import rnd.sueta.event_ms.dto.response.GetPlacesRs;
import rnd.sueta.event_ms.dto.response.GetReviewsRs;
import rnd.sueta.event_ms.mapper.PhotoMapper;
import rnd.sueta.event_ms.mapper.PlaceMapper;
import rnd.sueta.event_ms.mapper.ReviewMapper;
import rnd.sueta.event_ms.model.PhotoWithUrl;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;
import rnd.sueta.event_ms.model.entity.PhotoMeta;
import rnd.sueta.event_ms.model.entity.Review;
import rnd.sueta.event_ms.service.business.PhotoManager;
import rnd.sueta.event_ms.service.business.ReviewRegistrator;
import rnd.sueta.event_ms.service.entity.PlaceService;
import rnd.sueta.event_ms.service.entity.RecommendationService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements PlaceController {

    private final PlaceService placeService;
    private final PhotoManager placePhotoManager;
    private final ReviewRegistrator placeReviewRegistrator;
    private final RecommendationService recommendationService;

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

    @GetMapping("/recommended")
    public GetPlacesRs getAllRecommended(PaginationFilter paginationFilter) {
        Page<PlaceWithCoordinates> places = recommendationService.getAllPlaces(paginationFilter.page(), paginationFilter.size());

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
        return placeMapper.convert(placeService.getById(id));
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
        PlaceWithCoordinates place = placeMapper.convert(updatePlaceRq);

        return placeMapper.convert(placeService.update(id, place));
    }

    @Override
    public ReviewDto updateReview(UUID id, UUID reviewId, UpdateReviewRq updatereviewRq) {
        Review review = reviewMapper.convert(updatereviewRq);

        return reviewMapper.convert(placeReviewRegistrator.update(id, reviewId, review));
    }

    @Override
    public void delete(UUID id) {
        placeService.delete(id);
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
