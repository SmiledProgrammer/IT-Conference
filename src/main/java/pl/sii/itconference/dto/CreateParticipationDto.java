package pl.sii.itconference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CreateParticipationDto {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Long lectureId;
}
