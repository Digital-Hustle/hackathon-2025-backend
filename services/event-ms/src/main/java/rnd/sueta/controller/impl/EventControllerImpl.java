package rnd.sueta.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.controller.EventController;
import rnd.sueta.dto.EventDto;
import rnd.sueta.dto.EventWithPlaceDto;
import rnd.sueta.dto.PhotoMetaDto;
import rnd.sueta.dto.ReviewDto;
import rnd.sueta.dto.params.EventsFilter;
import rnd.sueta.dto.params.PaginationFilter;
import rnd.sueta.dto.request.CreateEventRq;
import rnd.sueta.dto.request.CreateReviewRq;
import rnd.sueta.dto.request.UpdateEventRq;
import rnd.sueta.dto.request.UpdateReviewRq;
import rnd.sueta.dto.response.GetEventsWithPlacesRs;
import rnd.sueta.dto.response.GetPhotosMetaWithUrlRs;
import rnd.sueta.dto.response.GetReviewsRs;
import rnd.sueta.mapper.EventMapper;
import rnd.sueta.mapper.PhotoMapper;
import rnd.sueta.mapper.RequestParamsMapper;
import rnd.sueta.mapper.ReviewMapper;
import rnd.sueta.model.EventFilterParams;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.PhotoWithUrl;
import rnd.sueta.model.entity.Event;
import rnd.sueta.model.entity.PhotoMeta;
import rnd.sueta.model.entity.Review;
import rnd.sueta.service.business.CacheProvider;
import rnd.sueta.service.business.EventRegistrationService;
import rnd.sueta.service.business.PhotoManager;
import rnd.sueta.service.business.ReviewRegistrator;
import rnd.sueta.service.entity.EventService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventService eventService;
    private final PhotoManager eventPhotoManager;
    private final ReviewRegistrator eventReviewRegistrator;
    private final EventRegistrationService eventRegistrationService;
    private final CacheProvider<EventWithPlace> cacheProvider;

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

    @Override
    public GetEventsWithPlacesRs getTop(PaginationFilter paginationFilter) {
        Page<EventWithPlace> events = cacheProvider.getTop(paginationFilter.page(), paginationFilter.size());

        return GetEventsWithPlacesRs.builder()
                .places(eventMapper.convert(events))
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
        return eventMapper.convert(cacheProvider.getById(id));
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
    public EventWithPlaceDto update(UUID id, UpdateEventRq updateEventRq) {
        EventWithPlace event = eventMapper.convertWithPlace(id, updateEventRq);

        return eventMapper.convert(cacheProvider.update(event));
    }

    @Override
    public ReviewDto updateReview(UUID id, UUID reviewId, UpdateReviewRq updatereviewRq) {
        Review review = reviewMapper.convert(reviewId, updatereviewRq);

        review = review.toBuilder()
                .id(reviewId)
                .build();

        return reviewMapper.convert(eventReviewRegistrator.update(id, review));
    }

    @Override
    public void delete(UUID id) {
        cacheProvider.delete(id);
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
