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
import rnd.sueta.event_ms.dto.EventDto;
import rnd.sueta.event_ms.dto.EventWithPlaceDto;
import rnd.sueta.event_ms.dto.PhotoMetaDto;
import rnd.sueta.event_ms.dto.ReviewDto;
import rnd.sueta.event_ms.dto.params.EventsFilter;
import rnd.sueta.event_ms.dto.params.PaginationFilter;
import rnd.sueta.event_ms.dto.request.CreateEventRq;
import rnd.sueta.event_ms.dto.request.CreateReviewRq;
import rnd.sueta.event_ms.dto.request.UpdateEventRq;
import rnd.sueta.event_ms.dto.request.UpdateReviewRq;
import rnd.sueta.event_ms.dto.response.GetEventsWithPlacesRs;
import rnd.sueta.event_ms.dto.response.GetPhotosMetaWithUrlRs;
import rnd.sueta.event_ms.dto.response.GetReviewsRs;

import java.util.UUID;

@RequestMapping(UrlPaths.EVENTS)
public interface EventController {

    @GetMapping
    GetEventsWithPlacesRs getAll(@Valid PaginationFilter paginationFilter, @Valid EventsFilter eventsFilter);

    @GetMapping(UrlPaths.RECOMMENDATIONS)
    GetEventsWithPlacesRs getTop(@Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.PHOTOS)
    GetPhotosMetaWithUrlRs getAllPhotosByOwnerId(@PathVariable UUID id, @Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.REVIEWS)
    GetReviewsRs getAllReviewsByOwnerId(@PathVariable UUID id, @Valid PaginationFilter paginationFilter);

    @GetMapping(UrlPaths.BY_ID)
    EventWithPlaceDto getById(@PathVariable UUID id);

    @GetMapping(UrlPaths.REVIEW_BY_ID)
    ReviewDto getReviewById(@PathVariable UUID reviewId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventDto create(@RequestBody @Valid CreateEventRq createEventRq);

    @PostMapping(UrlPaths.PHOTOS)
    @ResponseStatus(HttpStatus.CREATED)
    PhotoMetaDto uploadPhoto(@PathVariable UUID id, MultipartFile photo);

    @PostMapping(UrlPaths.REVIEWS)
    @ResponseStatus(HttpStatus.CREATED)
    ReviewDto createReview(@PathVariable UUID id, @RequestBody @Valid CreateReviewRq createReviewRq);

    @PutMapping(UrlPaths.BY_ID)
    EventWithPlaceDto update(@PathVariable UUID id, @RequestBody @Valid UpdateEventRq updateEventRq);

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
