package ru.ci_trainee.authms.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserWithCredentials(

        String email,

        String password,

        String passwordConfirmation
) {
}
