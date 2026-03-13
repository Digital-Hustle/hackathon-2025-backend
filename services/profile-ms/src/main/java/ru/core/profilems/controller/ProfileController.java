package ru.core.profilems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.core.profilems.dto.ProfileDto;
import ru.core.profilems.dto.response.PageRs;

import java.util.UUID;

@Tag(name = "Profile API", description = "Profile endpoints")
public interface ProfileController {
    @Operation(
            summary = "Receive all profiles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
            }
    )
    @GetMapping
    ResponseEntity<PageRs<ProfileDto>> getProfiles(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    );

    @Operation(
            summary = "Receive profiles by query",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
            }
    )
    @GetMapping("/search")
    ResponseEntity<PageRs<ProfileDto>> searchProfiles(
            @RequestParam("query") String query,
            @RequestParam(value = "ignoreCase", required = false, defaultValue = "false") boolean ignoreCase,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    );

    @Operation(
            summary = "Receive profile and its Tasks by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
                    @ApiResponse(responseCode = "404", description = "Profile not found")
            }
    )
    @GetMapping("/{profileId}")
    ResponseEntity<ProfileDto> getProfileById(UUID profileId);

    @Operation(
            summary = "Create new profile",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created profile"),
                    @ApiResponse(responseCode = "400", description = "ID should not be specified"),
                    @ApiResponse(responseCode = "400", description = "Validation failed")
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ProfileDto> createProfile(ProfileDto profileDto);

    @Operation(
            summary = "Update profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated profile"),
                    @ApiResponse(responseCode = "400", description = "ID mismatch"),
                    @ApiResponse(responseCode = "400", description = "Validation failed")
            }
    )
    ResponseEntity<ProfileDto> updateProfile(UUID profileId, ProfileDto profileDto);

    @Operation(
            summary = "Update profile",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted profile")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<HttpStatus> deleteProfile(@PathVariable UUID profileId);
}
