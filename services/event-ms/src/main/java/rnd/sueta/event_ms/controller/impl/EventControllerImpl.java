package rnd.sueta.event_ms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.controller.EventController;
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
import rnd.sueta.event_ms.mapper.EventMapper;
import rnd.sueta.event_ms.mapper.PhotoMapper;
import rnd.sueta.event_ms.mapper.RequestParamsMapper;
import rnd.sueta.event_ms.mapper.ReviewMapper;
import rnd.sueta.event_ms.model.EventFilterParams;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.PhotoWithUrl;
import rnd.sueta.event_ms.model.entity.Event;
import rnd.sueta.event_ms.model.entity.PhotoMeta;
import rnd.sueta.event_ms.model.entity.Review;
import rnd.sueta.event_ms.service.business.EventRegistrationService;
import rnd.sueta.event_ms.service.business.PhotoManager;
import rnd.sueta.event_ms.service.business.ReviewRegistrator;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.service.entity.RecommendationService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventService eventService;
    private final PhotoManager eventPhotoManager;
    private final ReviewRegistrator eventReviewRegistrator;
    private final EventRegistrationService eventRegistrationService;
    private final RecommendationService recommendationService;

    private final EventMapper eventMapper;
    private final PhotoMapper photoMapper;
    private final ReviewMapper reviewMapper;
    private final RequestParamsMapper requestParamsMapper;

    @Override
    public GetEventsWithPlacesRs getAll(PaginationFilter paginationFilter, EventsFilter eventsFilter) {
        EventFilterParams eventFilterParams = requestParamsMapper.convert(
                eventsFilter, paginationFilter.page(), paginationFilter.size()
        );

        Page<EventWithPlace> events = eventService.getAllByFilter(eventFilterParams);

        return GetEventsWithPlacesRs.builder()
                .places(eventMapper.convert(events))
                .build();
    }

    @GetMapping("/recommended")
    public GetEventsWithPlacesRs getAllRecommended(PaginationFilter paginationFilter) {
        Page<EventWithPlace> places = recommendationService.getAllEvents(paginationFilter.page(), paginationFilter.size());

        return GetEventsWithPlacesRs.builder()
                .places(eventMapper.convert(places))
                .build();
    }

    @Override
    public GetPhotosMetaWithUrlRs getAllPhotosByOwnerId(UUID id, PaginationFilter paginationFilter) {
        Page<PhotoWithUrl> photosMetaWithUrl = eventPhotoManager.getAllByOwnerId(
                id, paginationFilter.page(), paginationFilter.size()
        );

        return GetPhotosMetaWithUrlRs.builder()
                .photosMetaWithUrl(photoMapper.convert(photosMetaWithUrl))
                .build();
    }

    @Override
    public GetReviewsRs getAllReviewsByOwnerId(UUID id, PaginationFilter paginationFilter) {
        Page<Review> reviews = eventReviewRegistrator.getAllByOwnerId(
                id, paginationFilter.page(), paginationFilter.size()
        );

        return GetReviewsRs.builder()
                .reviews(reviewMapper.convert(reviews))
                .build();
    }

    @Override
    public EventWithPlaceDto getById(UUID id) {
        return eventMapper.convert(eventService.getEventWithPlaceById(id));
    }

    @Override
    public ReviewDto getReviewById(UUID reviewId) {
        Review review = eventReviewRegistrator.getById(reviewId);

        return reviewMapper.convert(review);
    }

    @Override
    public EventDto create(CreateEventRq createEventRq) {
        Event event = eventMapper.convert(createEventRq);

        return eventMapper.convert(eventRegistrationService.createEvent(event));
    }

    @Override
    public PhotoMetaDto uploadPhoto(UUID id, MultipartFile photo) {
        PhotoMeta photoMeta = eventPhotoManager.createPhoto(id, photo);

        return photoMapper.convert(photoMeta);
    }

    @Override
    public ReviewDto createReview(UUID id, CreateReviewRq createReviewRq) {
        Review review = reviewMapper.convert(createReviewRq);

        return reviewMapper.convert(eventReviewRegistrator.register(id, review));
    }

    @Override
    public EventDto update(UUID id, UpdateEventRq updateEventRq) {
        Event event = eventMapper.convert(updateEventRq);

        return eventMapper.convert(eventRegistrationService.updateEvent(id, event));
    }

    @Override
    public ReviewDto updateReview(UUID id, UUID reviewId, UpdateReviewRq updatereviewRq) {
        Review review = reviewMapper.convert(updatereviewRq);

        review = review.toBuilder()
                .id(reviewId)
                .build();

        return reviewMapper.convert(eventReviewRegistrator.update(id, reviewId, review));
    }

    @Override
    public void delete(UUID id) {
        eventService.delete(id);
    }

    @Override
    public void deletePhoto(UUID photoId) {
        eventPhotoManager.deletePhoto(photoId);
    }

    @Override
    public void deleteReview(UUID id, UUID reviewId) {
        eventReviewRegistrator.delete(id, reviewId);
    }
}
