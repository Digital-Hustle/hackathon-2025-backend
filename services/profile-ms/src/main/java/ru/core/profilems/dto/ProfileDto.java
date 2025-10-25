package ru.core.profilems.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.core.profilems.validation.OnCreate;
import ru.core.profilems.validation.OnUpdate;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Profile data transfer object")
public class ProfileDto {
    @NotNull(message = "Id can't be null", groups = OnUpdate.class)
    @Schema(description = "Profile ID", example = "63ba3c9a-4c86-4a24-8e8a-5601edc55ef4", type = "UUID")
    private UUID profileId;

    @NotNull(message = "Name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 2, max = 100, message = "Name should be in range between 2 and 100 chars", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Profile name", example = "Danil", type = "String")
    private String name;

    @NotNull(message = "Surname must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 2, max = 100, message = "Surname should be in range between 2 and 100 chars", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Surname", example = "Chetvyrtov", type = "String")
    private String surname;

    @NotNull(message = "birth date must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Schema(description = "Birth date", example = "2020-05-03", type = "Integer")
    private LocalDate dateOfBirth;

    private String image;
}
