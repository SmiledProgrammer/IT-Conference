package pl.sii.itconference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UserDto {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;
}
