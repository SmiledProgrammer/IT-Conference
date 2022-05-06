package pl.sii.itconference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ParticipationDto {
    @NotBlank
    private String username;

    @NotNull
    private Long lectureId;
}
