package ru.core.profilems.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.core.profilems.controller.ProfileController;
import ru.core.profilems.dto.ProfileDto;
import ru.core.profilems.dto.request.SearchParametersRq;
import ru.core.profilems.dto.response.PageRs;
import ru.core.profilems.mapper.ProfileMapper;
import ru.core.profilems.model.Profile;
import ru.core.profilems.service.ProfileService;
import ru.core.profilems.validation.OnCreate;
import ru.core.profilems.validation.OnUpdate;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Override
    public ResponseEntity<PageRs<ProfileDto>> getProfiles(Integer page, Integer size) {
        Page<Profile> pageEntity = profileService.getAllProfiles(page, size);
        var response = toPageResponse(pageEntity, profileMapper::toDto);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<PageRs<ProfileDto>> searchProfiles(
            String query, boolean ignoreCase, Integer page, Integer size
    ) {
        var searchParams = SearchParametersRq.builder()
                .query(query)
                .ignoreCase(ignoreCase)
                .page(page)
                .size(size)
                .build();

        Page<Profile> pageEntity = profileService.search(searchParams);
        var response = toPageResponse(pageEntity, profileMapper::toDto);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{profileId}")
    @Override
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable("profileId") UUID profileId) {
        var profile = profileService.getProfile(profileId);
        var profileDto = profileMapper.toDto(profile);

        return ResponseEntity.ok().body(profileDto);
    }

    @PostMapping
    @Override
    public ResponseEntity<ProfileDto> createProfile(
            @RequestBody @Validated(OnCreate.class) ProfileDto profileDto) {
        var profile = profileMapper.toEntity(profileDto);
        profile = profileService.create(profile);

        return ResponseEntity
                .created(URI.create("/api/v1/profile/" + profile.getProfileId()))
                .body(profileMapper.toDto(profile));
    }

    @PutMapping("/{profileId}")
    @Override
    public ResponseEntity<ProfileDto> updateProfile(
            @PathVariable(name = "profileId") UUID profileId,
            @RequestBody @Validated(OnUpdate.class) ProfileDto profileDto) {
        var profile = profileMapper.toEntity(profileDto);
        profile = profileService.update(profileId, profile);

        return ResponseEntity.ok().body(profileMapper.toDto(profile));
    }

    @DeleteMapping("/{profileId}")
    @Override
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable UUID profileId) {
        profileService.delete(profileId);
        return ResponseEntity.noContent().build();
    }

    private <T, R> PageRs<R> toPageResponse(Page<T> pageEntity, Function<T, R> mapper) {
        List<R> profiles = pageEntity.getContent().stream().map(mapper).toList();

        return PageRs.<R>builder()
                .content(profiles)
                .totalPages(pageEntity.getTotalPages())
                .totalElements(pageEntity.getTotalElements())
                .curPage(pageEntity.getNumber() + 1)
                .pageSize(pageEntity.getSize())
                .build();
    }
}
