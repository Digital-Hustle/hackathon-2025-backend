package ru.core.profilems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.core.profilems.constants.ContextKeys;
import ru.core.profilems.dto.CurrentUser;
import ru.core.profilems.dto.request.SearchParametersRq;
import ru.core.profilems.exception.exception.PageNotFound;
import ru.core.profilems.exception.exception.ProfileNotFoundException;
import ru.core.profilems.model.Profile;
import ru.core.profilems.repository.ProfileRepository;

import java.util.UUID;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Page<Profile> getAllProfiles(Integer page, Integer size) {
        var pageEntity = profileRepository.findAll(PageRequest.of(page - 1, size));

        if (pageEntity.getTotalPages() < page) {
            throw new PageNotFound("Such page does not exist");
        }

        return pageEntity;
    }

    public Page<Profile> search(SearchParametersRq searchParametersRq) {
        BiFunction<String, PageRequest, Page<Profile>> method = searchParametersRq.ignoreCase()
                ? profileRepository::searchAnywhereInNameOrSurnameIgnoreCase
                : profileRepository::searchAnywhereInNameOrSurname;

        var pageRq = PageRequest.of(searchParametersRq.page() - 1, searchParametersRq.size());
        var pageEntity = method.apply(searchParametersRq.query(), pageRq);

        if (pageEntity.getTotalPages() < searchParametersRq.page()) {
            throw new PageNotFound("Such page does not exist");
        }

        return pageEntity;
    }

    public Profile getProfile(UUID profileId) {
        return profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);
    }

    @Transactional
    public Profile create(Profile profile) {
        UUID profileId = CurrentUser.get(ContextKeys.USER_ID, UUID.class);
        Profile profileWithId = profile.toBuilder()
                .profileId(profileId)
                .build();

        if (profileRepository.existsById(profile.getProfileId())) {
            throw new IllegalArgumentException("Profile with ID " + profile.getProfileId() + " already exists");
        }

        return profileRepository.save(profileWithId);
    }

    @Transactional
    public Profile update(UUID profileId, Profile profile) {
        if (!profileId.equals(profile.getProfileId())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        var existedProfile = getProfile(profileId);

        Profile updatedProfile = existedProfile.toBuilder()
                .name(profile.getName())
                .surname(profile.getSurname())
                .build();

        return profileRepository.save(updatedProfile);
    }

    @Transactional
    public void delete(UUID profileId) {
        profileRepository.deleteById(profileId);
    }
}
