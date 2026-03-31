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

@RequestMapping("/api/v1/events")
public interface EventController {

    @GetMapping
    GetEventsWithPlacesRs getAll(@Valid PaginationFilter paginationFilter, @Valid EventsFilter eventsFilter);

    @GetMapping("/{id}/photos")
    GetPhotosMetaWithUrlRs getAllPhotosByOwnerId(@PathVariable UUID id, @Valid PaginationFilter paginationFilter);

    @GetMapping("/{id}/reviews")
    GetReviewsRs getAllReviewsByOwnerId(@PathVariable UUID id, @Valid PaginationFilter paginationFilter);

    @GetMapping("/{id}")
    EventWithPlaceDto getById(@PathVariable UUID id);

    @GetMapping("/{id}/reviews/{reviewId}")
    ReviewDto getReviewById(@PathVariable UUID reviewId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventDto create(@RequestBody @Valid CreateEventRq createEventRq);

    @PostMapping("/{id}/photos")
    @ResponseStatus(HttpStatus.CREATED)
    PhotoMetaDto uploadPhoto(@PathVariable UUID id, MultipartFile photo);

    @PostMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    ReviewDto createReview(@PathVariable UUID id, @RequestBody @Valid CreateReviewRq createReviewRq);

    @PutMapping("/{id}")
    EventDto update(@PathVariable UUID id, @RequestBody @Valid UpdateEventRq updateEventRq);

    @PutMapping("/{id}/reviews/{reviewId}")
    ReviewDto updateReview(@PathVariable UUID id, @PathVariable UUID reviewId, @RequestBody @Valid UpdateReviewRq updatereviewRq);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id);

    @DeleteMapping("/{id}/photos/{linkedTableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhoto(@PathVariable UUID photoId);

    @DeleteMapping("/{id}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReview(@PathVariable UUID id, @PathVariable UUID reviewId);
}
