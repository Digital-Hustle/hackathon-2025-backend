package rnd.sueta.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserWithCredentials(

        String email,

        String password,

        String passwordConfirmation
) {
}
