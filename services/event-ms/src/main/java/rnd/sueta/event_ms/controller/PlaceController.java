package rnd.sueta.event_ms.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.constants.UrlPaths;
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

import java.util.UUID;

@RequestMapping(UrlPaths.PLACES)
public interface PlaceController {

    @GetMapping
    GetPlacesRs getAll(@Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.RECOMMENDATIONS)
    GetPlacesRs getTop(@Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.PHOTOS)
    GetPhotosMetaWithUrlRs getAllByOwnerId(@PathVariable UUID id, @Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.REVIEWS)
    GetReviewsRs getAllReviewsByOwnerId(@PathVariable UUID id, @Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.BY_ID)
    PlaceDto getById(@PathVariable UUID id);

    @GetMapping(UrlPaths.REVIEW_BY_ID)
    ReviewDto getReviewById(@PathVariable UUID reviewId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PlaceDto create(@RequestBody @Valid CreatePlaceRq createPlaceRq);

    @PostMapping(UrlPaths.PHOTOS)
    @ResponseStatus(HttpStatus.CREATED)
    PhotoMetaDto uploadPhoto(@PathVariable UUID id, MultipartFile photo);

    @PostMapping(UrlPaths.REVIEWS)
    @ResponseStatus(HttpStatus.CREATED)
    ReviewDto createReview(@PathVariable UUID id, @RequestBody @Valid CreateReviewRq createReviewRq);

    @PutMapping(UrlPaths.BY_ID)
    PlaceDto update(@PathVariable UUID id, @RequestBody @Valid UpdatePlaceRq updatePlaceRq);

    @PutMapping(UrlPaths.REVIEW_BY_ID)
    ReviewDto updateReview(@PathVariable UUID id, @PathVariable UUID reviewId, @RequestBody @Valid UpdateReviewRq updatereviewRq);

    @DeleteMapping(UrlPaths.BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping(UrlPaths.PHOTO_BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhoto(@PathVariable UUID photoId);

    @DeleteMapping(UrlPaths.REVIEW_BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReview(@PathVariable UUID id, @PathVariable UUID reviewId);
}
