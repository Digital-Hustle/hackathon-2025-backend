package ru.ci_trainee.authms.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record JwtData(

        UUID id,

        String email,

        String accessToken,

        String refreshToken
) {
}
