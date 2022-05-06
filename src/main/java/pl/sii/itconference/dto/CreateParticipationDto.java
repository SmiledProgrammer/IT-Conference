package pl.sii.itconference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class CreateParticipationDto {
    @NotBlank
    @Size(min = 4, max = 32)
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Long lectureId;
}
